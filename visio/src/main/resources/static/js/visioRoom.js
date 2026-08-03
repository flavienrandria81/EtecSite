(function () {
    'use strict';

    const roomData = document.getElementById('roomData');

    const roomCode = roomData.dataset.code;
    const reunionTitre = roomData.dataset.titre;
    const reunionStatut = roomData.dataset.statut;
    const salleId = roomData.dataset.salleId;
    const enseignantId = roomData.dataset.enseignantId;

    function getToken() {
        return localStorage.getItem('token')
            || localStorage.getItem('jwt')
            || sessionStorage.getItem('token')
            || '';
    }

    function authHeaders(json) {
        const h = {};
        if (json) h['Content-Type'] = 'application/json';
        const t = getToken();
        if (t) h['Authorization'] = 'Bearer ' + t;
        return h;
    }

    const ICE_CONFIG = {
        iceServers: [
            {urls: 'stun:stun.l.google.com:19302'},
            {urls: 'stun:stun1.l.google.com:19302'}
        ]
    };

    try {
        const turn = JSON.parse(roomData.dataset.turn || '{}');
        if (turn.iceServers && turn.iceServers.length) {
            ICE_CONFIG.iceServers = ICE_CONFIG.iceServers.concat(turn.iceServers);
        }
    } catch (e) {
        console.warn('Configuration TURN invalide', e);
    }

    const elements = {
        lobby: document.getElementById('lobby'),
        lobbyForm: document.getElementById('lobbyForm'),
        lobbyName: document.getElementById('lobbyName'),
        lobbyRole: document.getElementById('lobbyRole'),
        lobbyUserId: document.getElementById('lobbyUserId'),
        room: document.getElementById('room'),
        videoGrid: document.getElementById('videoGrid'),
        participantCount: document.getElementById('participantCount'),
        myRoleBadge: document.getElementById('myRoleBadge'),
        leaveBtn: document.getElementById('leaveBtn'),
        leaveBtn2: document.getElementById('leaveBtn2'),
        micBtn: document.getElementById('micBtn'),
        camBtn: document.getElementById('camBtn'),
        screenBtn: document.getElementById('screenBtn'),
        chatOpenBtn: document.getElementById('chatOpenBtn'),
        chatToggle: document.getElementById('chatToggle'),
        chatPanel: document.getElementById('chatPanel'),
        chatMessages: document.getElementById('chatMessages'),
        chatForm: document.getElementById('chatForm'),
        chatText: document.getElementById('chatText'),
        handBtn: document.getElementById('handBtn'),
        raiseHandBanner: document.getElementById('raiseHandBanner'),
        raiseHandText: document.getElementById('raiseHandText'),
        raiseHandClose: document.getElementById('raiseHandClose')
    };

    let stompClient = null;
    let localStream = null;
    let localTileId = null;
    let myId = null;
    let myName = '';
    let myRole = '';
    let myUserId = null;
    let screenTrack = null;

    const peers = new Map();
    const remoteStreams = new Map();
    const roomParticipants = new Map();

    const savedName = localStorage.getItem('visio_name');
    const savedRole = localStorage.getItem('visio_role');

    if (savedName) elements.lobbyName.value = savedName;
    if (savedRole) elements.lobbyRole.value = savedRole;

    /* ---------------- Salle ---------------- */

    function showRoom() {
        elements.lobby.classList.add('hidden');
        elements.room.classList.remove('hidden');
        elements.myRoleBadge.textContent = myRole === 'ENSEIGNANT'
            ? 'Enseignant' : 'Étudiant';
        elements.myRoleBadge.classList.remove('hidden');
    }

    function uuid() {
        return crypto.randomUUID
            ? crypto.randomUUID()
            : 'id-' + Date.now() + '-' + Math.random().toString(36).slice(2, 10);
    }

    function myPeerId() {
        return myId || localTileId;
    }

    /* ---------------- Connexion WebSocket ---------------- */

    function connectWebSocket() {

        const socket = new SockJS('/visio-ws');
        stompClient = Stomp.over(socket);
        stompClient.debug = function () {};

        stompClient.connect({}, onConnected, function () {
            toast('Connexion impossible au serveur visio');
        });
    }

    function onConnected() {

        stompClient.subscribe(
            '/topic/reunion/' + roomCode + '/welcome', onWelcome);
        stompClient.subscribe(
            '/topic/reunion/' + roomCode + '/signal', onSignal);
        stompClient.subscribe(
            '/topic/reunion/' + roomCode + '/presence', onPresence);
        stompClient.subscribe(
            '/topic/reunion/' + roomCode + '/chat', onChat);

        stompClient.send(
            '/app/reunion/' + roomCode + '/join',
            {},
            JSON.stringify({
                type: 'join',
                from: localTileId,
                payload: JSON.stringify({
                    userId: myUserId,
                    nom: myName,
                    role: myRole
                })
            })
        );
    }

    function onWelcome(raw) {
        const s = JSON.parse(raw.body);
        if (s.to && s.to !== localTileId) return;
        myId = s.sessionId || s.to;
    }

    function sendSignal(type, to, payload) {
        if (!stompClient || !stompClient.connected) return;
        stompClient.send(
            '/app/reunion/' + roomCode + '/signal',
            {},
            JSON.stringify({type: type, from: myPeerId(), to: to, payload: payload})
        );
    }

    /* ---------------- Signalisation ---------------- */

    function onSignal(raw) {
        const s = JSON.parse(raw.body);

        if (s.type === 'room-info' && s.to === myId) {
            const existing = JSON.parse(s.payload);
            existing.forEach(p => connectToPeer(p));
            return;
        }

        if (s.to && s.to !== myId) return;

        switch (s.type) {
            case 'offer':
                handleOffer(s.from, JSON.parse(s.payload));
                break;
            case 'answer':
                handleAnswer(s.from, JSON.parse(s.payload));
                break;
            case 'candidate':
                handleCandidate(s.from, JSON.parse(s.payload));
                break;
            default:
                break;
        }
    }

    function onPresence(raw) {
        const p = JSON.parse(raw.body);

        if (p.type === 'peer-joined') {
            const participant = JSON.parse(p.payload);
            if (participant.sessionId === myId) return;
            roomParticipants.set(participant.sessionId, participant);
            addParticipantTile(participant.sessionId, participant, null);
            updateCount();
        }

        if (p.type === 'peer-left') {
            const participant = JSON.parse(p.payload);
            removePeer(participant.sessionId);
        }

        if (p.type === 'raise-hand') {
            const info = JSON.parse(p.payload);
            showRaiseHand(info.nom);
        }

        if (p.type === 'lower-hand') {
            hideRaiseHand();
        }
    }

    function onChat(raw) {
        appendChat(JSON.parse(raw.body));
    }

    /* ---------------- WebRTC (maille) ---------------- */

    function connectToPeer(participant) {

        roomParticipants.set(participant.sessionId, participant);
        addParticipantTile(participant.sessionId, participant, null);
        updateCount();

        createPeerConnection(participant.sessionId, participant, true);
    }

    function createPeerConnection(sessionId, participant, initiator) {

        const pc = new RTCPeerConnection(ICE_CONFIG);

        peers.set(sessionId, pc);

        if (localStream) {
            localStream.getTracks().forEach(t => pc.addTrack(t, localStream));
        }

        pc.onicecandidate = function (event) {
            if (event.candidate) {
                sendSignal('candidate', sessionId,
                    JSON.stringify(event.candidate));
            }
        };

        pc.ontrack = function (event) {
            const stream = event.streams[0];
            if (stream && !remoteStreams.has(sessionId)) {
                remoteStreams.set(sessionId, stream);
                attachStreamToTile(sessionId, stream);

                stream.getAudioTracks().forEach(t => {
                    t.onmute = function () {
                        updateRemoteMuteState(sessionId, true);
                    };
                    t.onunmute = function () {
                        updateRemoteMuteState(sessionId, false);
                    };
                });
            }
        };

        pc.onconnectionstatechange = function () {
            if (pc.connectionState === 'failed'
                || pc.connectionState === 'closed'
                || pc.connectionState === 'disconnected') {
                removePeer(sessionId);
            }
        };

        if (initiator) {
            pc.createOffer()
                .then(offer => pc.setLocalDescription(offer))
                .then(() => sendSignal('offer', sessionId,
                    JSON.stringify(pc.localDescription)))
                .catch(err => console.error('Erreur offer', err));
        }

        return pc;
    }

    function handleOffer(from, offer) {

        const participant = roomParticipants.get(from)
            || {sessionId: from, nom: 'Participant', role: 'ETUDIANT'};

        const pc = createPeerConnection(from, participant, false);

        pc.setRemoteDescription(offer)
            .then(() => pc.createAnswer())
            .then(answer => pc.setLocalDescription(answer))
            .then(() => sendSignal('answer', from,
                JSON.stringify(pc.localDescription)))
            .catch(err => console.error('Erreur answer', err));
    }

    function handleAnswer(from, answer) {
        const pc = peers.get(from);
        if (pc) {
            pc.setRemoteDescription(answer)
                .catch(err => console.error('Erreur setRemoteDescription', err));
        }
    }

    function handleCandidate(from, candidate) {
        const pc = peers.get(from);
        if (pc) {
            pc.addIceCandidate(candidate)
                .catch(err => console.error('Erreur addIceCandidate', err));
        }
    }

    function removePeer(sessionId) {

        const pc = peers.get(sessionId);
        if (pc) {
            pc.close();
            peers.delete(sessionId);
        }

        remoteStreams.delete(sessionId);
        roomParticipants.delete(sessionId);

        const tile = document.getElementById('tile-' + sessionId);
        if (tile) tile.remove();

        updateCount();
    }

    /* ---------------- Rendu vidéo ---------------- */

    function addParticipantTile(sessionId, participant, stream) {

        const existing = document.getElementById('tile-' + sessionId);
        if (existing) {
            if (stream) attachStreamToTile(sessionId, stream);
            return;
        }

        const tile = document.createElement('div');
        tile.className = 'video-tile';
        tile.id = 'tile-' + sessionId;

        const video = document.createElement('video');
        video.autoplay = true;
        video.playsInline = true;

        const avatar = document.createElement('div');
        avatar.className = 'avatar';
        avatar.innerHTML = '&#128100;';

        if (stream) {
            video.srcObject = stream;
            tile.appendChild(video);
        } else {
            tile.appendChild(avatar);
        }

        const label = document.createElement('div');
        label.className = 'video-label';
        const icon = document.createElement('span');
        icon.innerHTML = participant.role === 'ENSEIGNANT' ? '&#127979;' : '&#127891;';
        label.appendChild(icon);
        const nameSpan = document.createElement('span');
        nameSpan.textContent = participant.nom;
        label.appendChild(nameSpan);

        const mutedOverlay = document.createElement('div');
        mutedOverlay.className = 'muted-overlay hidden';
        mutedOverlay.textContent = 'Micro coupé';

        tile.appendChild(label);
        tile.appendChild(mutedOverlay);

        elements.videoGrid.appendChild(tile);
    }

    function attachStreamToTile(sessionId, stream) {

        const tile = document.getElementById('tile-' + sessionId);
        if (!tile) return;

        const avatar = tile.querySelector('.avatar');
        if (avatar) avatar.remove();

        const video = tile.querySelector('video');

        if (video) {
            video.srcObject = stream;
        } else {
            const v = document.createElement('video');
            v.autoplay = true;
            v.playsInline = true;
            v.srcObject = stream;
            tile.prepend(v);
        }
    }

    function updateCount() {
        const count = roomParticipants.size;
        elements.participantCount.textContent = count;
    }

    function updateRemoteMuteState(sessionId, muted) {
        const tile = document.getElementById('tile-' + sessionId);
        if (!tile) return;
        const overlay = tile.querySelector('.muted-overlay');
        if (overlay) overlay.classList.toggle('hidden', !muted);
    }

    /* ---------------- Caméra & micro ---------------- */

    function toggleMic() {

        if (!localStream) return;

        const audioTracks = localStream.getAudioTracks();
        const enabled = !audioTracks.every(t => !t.enabled);

        audioTracks.forEach(t => t.enabled = !enabled);

        elements.micBtn.classList.toggle('off', enabled);
        elements.micBtn.classList.toggle('active', !enabled);
    }

    function toggleCam() {

        if (!localStream) return;

        const videoTracks = localStream.getVideoTracks();
        const enabled = videoTracks.some(t => t.enabled);

        videoTracks.forEach(t => t.enabled = !enabled);

        const myTile = document.getElementById('tile-' + localTileId);
        if (myTile) {
            const avatar = myTile.querySelector('.avatar');
            const video = myTile.querySelector('video');
            if (avatar) avatar.classList.toggle('hidden', enabled);
            if (video) video.classList.toggle('hidden', !enabled);
        }

        elements.camBtn.classList.toggle('off', enabled);
        elements.camBtn.classList.toggle('active', !enabled);
    }

    async function toggleScreenShare() {

        if (screenTrack) {
            stopScreenShare();
            return;
        }

        try {
            const stream = await navigator.mediaDevices.getDisplayMedia({
                video: true
            });

            screenTrack = stream.getVideoTracks()[0];

            screenTrack.onended = function () {
                stopScreenShare();
            };

            const oldVideoTrack = localStream.getVideoTracks()[0];

            localStream.removeTrack(oldVideoTrack);
            localStream.addTrack(screenTrack);

            peers.forEach(pc => {
                const sender = pc.getSenders()
                    .find(s => s.track && s.track.kind === 'video');
                if (sender) sender.replaceTrack(screenTrack);
            });

            elements.screenBtn.classList.add('off');

        } catch (e) {
            toast('Partage d\'écran annulé ou indisponible');
        }
    }

    function stopScreenShare() {

        if (!screenTrack) return;

        screenTrack.stop();

        if (localStream && localStream.getVideoTracks().length > 0) {
            const camTrack = localStream.getVideoTracks()[0];
            peers.forEach(pc => {
                const sender = pc.getSenders()
                    .find(s => s.track && s.track.kind === 'video');
                if (sender) sender.replaceTrack(camTrack);
            });
        }

        screenTrack = null;
        elements.screenBtn.classList.remove('off');
    }

    /* ---------------- Chat ---------------- */

    function sendChat() {

        const text = elements.chatText.value.trim();
        if (!text) return;

        stompClient.send(
            '/app/reunion/' + roomCode + '/chat',
            {},
            JSON.stringify({
                from: myPeerId(),
                nom: myName,
                role: myRole,
                message: text
            })
        );

        elements.chatText.value = '';
    }

    function appendChat(message) {

        const div = document.createElement('div');
        div.className = 'chat-msg' + (message.from === myPeerId() ? ' me' : '');

        const meta = document.createElement('div');
        meta.className = 'chat-meta';

        const time = new Date().toLocaleTimeString('fr-FR',
            {hour: '2-digit', minute: '2-digit'});

        meta.innerHTML = message.nom +
            ' <span class="chat-time">' + time + '</span>';

        const body = document.createElement('div');
        body.textContent = message.message;

        div.appendChild(meta);
        div.appendChild(body);

        elements.chatMessages.appendChild(div);
        elements.chatMessages.scrollTop = elements.chatMessages.scrollHeight;
    }

    /* ---------------- Lever la main ---------------- */

    function toggleRaiseHand() {

        const type = elements.handBtn.classList.contains('off')
            ? 'lower-hand' : 'raise-hand';

        if (type === 'raise-hand') {
            elements.handBtn.classList.add('off');
        } else {
            elements.handBtn.classList.remove('off');
        }

        stompClient.send(
            '/app/reunion/' + roomCode + '/raisehand',
            {},
            JSON.stringify({
                type: type,
                from: myPeerId(),
                payload: JSON.stringify({nom: myName, role: myRole})
            })
        );
    }

    function showRaiseHand(nom) {
        elements.raiseHandText.textContent =
            nom + ' lève la main';
        elements.raiseHandBanner.classList.remove('hidden');
    }

    function hideRaiseHand() {
        elements.raiseHandBanner.classList.add('hidden');
    }

    /* ---------------- Divers ---------------- */

    function toast(message) {
        let el = document.getElementById('toast');
        if (!el) {
            el = document.createElement('div');
            el.id = 'toast';
            el.className = 'toast hidden';
            document.body.appendChild(el);
        }
        el.textContent = message;
        el.classList.remove('hidden');
        setTimeout(() => el.classList.add('hidden'), 4000);
    }

    function leaveRoom() {

        if (screenTrack) screenTrack.stop();

        peers.forEach(pc => {
            try { pc.close(); } catch (e) {}
        });
        peers.clear();

        if (localStream) {
            localStream.getTracks().forEach(t => t.stop());
        }

        const token = getToken();
        if (token && roomCode) {
            try {
                fetch('/api/visio/salles/code/' + roomCode + '/quitter', {
                    method: 'POST',
                    headers: authHeaders(true)
                }).catch(() => {});
            } catch (e) {}
        }

        if (stompClient) {
            try { stompClient.disconnect(); } catch (e) {}
        }

        window.location.href = '/visio';
    }

    /* ---------------- Événements ---------------- */

    elements.lobbyForm.addEventListener('submit', function (event) {

        event.preventDefault();

        myName = elements.lobbyName.value.trim();
        myRole = elements.lobbyRole.value;

        if (!myName) return;

        myUserId = elements.lobbyUserId
            && elements.lobbyUserId.value
            ? parseInt(elements.lobbyUserId.value, 10)
            : null;

        localStorage.setItem('visio_name', myName);
        localStorage.setItem('visio_role', myRole);

        localTileId = uuid();

        const entreDansSalle = function () {
            showRoom();

            navigator.mediaDevices.getUserMedia({video: true, audio: true})
                .then(function (stream) {

                    localStream = stream;

                    const me = {
                        sessionId: localTileId,
                        nom: myName + ' (vous)',
                        role: myRole
                    };

                    roomParticipants.set(localTileId, me);
                    addParticipantTile(localTileId, me, stream);
                    updateCount();

                    connectWebSocket();
                })
                .catch(function () {
                    toast('Caméra ou micro non accessible');
                });
        };

        const token = getToken();

        if (!token) {
            entreDansSalle();
            return;
        }

        fetch('/api/visio/salles/code/' + roomCode + '/rejoindre', {
            method: 'POST',
            headers: authHeaders(true),
            body: JSON.stringify({nom: myName, role: myRole})
        })
            .then(r => {
                if (!r.ok) throw new Error('Accès refusé à la visioconférence');
                return r.json();
            })
            .then(join => {
                if (!join.autorise) throw new Error(join.message || 'Accès refusé');
                myUserId = join.participant
                    ? join.participant.utilisateurId
                    : myUserId;
                entreDansSalle();
            })
            .catch(err => {
                elements.lobbyError.textContent =
                    err.message || 'Accès refusé à la visioconférence';
                elements.lobbyError.classList.remove('hidden');
            });
    });

    elements.micBtn.addEventListener('click', toggleMic);
    elements.camBtn.addEventListener('click', toggleCam);
    elements.screenBtn.addEventListener('click', toggleScreenShare);
    elements.handBtn.addEventListener('click', toggleRaiseHand);

    elements.leaveBtn.addEventListener('click', leaveRoom);
    elements.leaveBtn2.addEventListener('click', leaveRoom);

    elements.chatOpenBtn.addEventListener('click', function () {
        elements.chatPanel.classList.remove('hidden');
    });

    elements.chatToggle.addEventListener('click', function () {
        elements.chatPanel.classList.add('hidden');
    });

    elements.chatForm.addEventListener('submit', function (event) {
        event.preventDefault();
        sendChat();
    });

    elements.raiseHandClose.addEventListener('click', hideRaiseHand);

    window.addEventListener('beforeunload', function () {
        peers.forEach(pc => {
            try { pc.close(); } catch (e) {}
        });
    });
})();

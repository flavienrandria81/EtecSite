-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : lun. 03 août 2026 à 12:29
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `etec`
--

-- --------------------------------------------------------

--
-- Structure de la table `actualites`
--

CREATE TABLE `actualites` (
  `id` bigint(20) NOT NULL,
  `categorie` enum('EVENEMENT','FLASH_INFO','PEDAGOGIE','VIE8ETUDIANT') DEFAULT NULL,
  `description` longtext DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `important` bit(1) DEFAULT NULL,
  `status` enum('ARCHIVE','BROUILLON','PUBLIE') DEFAULT NULL,
  `titre` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `admins`
--

CREATE TABLE `admins` (
  `id` bigint(20) NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `admins`
--

INSERT INTO `admins` (`id`, `first_name`, `last_name`, `phone_number`, `user_id`) VALUES
(1, 'Jean', 'Rakoto', '0340000000', 2);

-- --------------------------------------------------------

--
-- Structure de la table `annees_univ`
--

CREATE TABLE `annees_univ` (
  `id` bigint(20) NOT NULL,
  `actif` bit(1) NOT NULL,
  `debut` date DEFAULT NULL,
  `fin` date DEFAULT NULL,
  `libelle` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `chapitres`
--

CREATE TABLE `chapitres` (
  `id` bigint(20) NOT NULL,
  `description` varchar(3000) DEFAULT NULL,
  `ordre` int(11) DEFAULT NULL,
  `titre` varchar(255) DEFAULT NULL,
  `cours_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `conversations`
--

CREATE TABLE `conversations` (
  `id` bigint(20) NOT NULL,
  `date_creation` datetime(6) DEFAULT NULL,
  `destinataire_id` bigint(20) DEFAULT NULL,
  `expediteur_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `cours`
--

CREATE TABLE `cours` (
  `id` bigint(20) NOT NULL,
  `actif` bit(1) NOT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `domaine_id` bigint(20) DEFAULT NULL,
  `enseignant_id` bigint(20) DEFAULT NULL,
  `filiere_id` bigint(20) DEFAULT NULL,
  `matiere_id` bigint(20) DEFAULT NULL,
  `niveau_id` bigint(20) DEFAULT NULL,
  `titre` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `devoirs`
--

CREATE TABLE `devoirs` (
  `id` bigint(20) NOT NULL,
  `cours_id` bigint(20) DEFAULT NULL,
  `date_limite` date DEFAULT NULL,
  `description` varchar(3000) DEFAULT NULL,
  `note_max` double DEFAULT NULL,
  `titre` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `documents`
--

CREATE TABLE `documents` (
  `id` bigint(20) NOT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `taille` double DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `leçon_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `domaine`
--

CREATE TABLE `domaine` (
  `id` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `domaine`
--

INSERT INTO `domaine` (`id`, `description`, `nom`) VALUES
(1, 'fnjgrhjgjvdgjrgrjghrjghrg', 'Science de societe');

-- --------------------------------------------------------

--
-- Structure de la table `emploi_du_temps`
--

CREATE TABLE `emploi_du_temps` (
  `id` bigint(20) NOT NULL,
  `date` date DEFAULT NULL,
  `enseignant_id` bigint(20) DEFAULT NULL,
  `etudiant_id` bigint(20) DEFAULT NULL,
  `filiere_id` bigint(20) DEFAULT NULL,
  `heure_debut` time(6) DEFAULT NULL,
  `heure_fin` time(6) DEFAULT NULL,
  `jours` varchar(255) DEFAULT NULL,
  `matiere_id` bigint(20) DEFAULT NULL,
  `niveau_id` bigint(20) DEFAULT NULL,
  `salle` varchar(255) DEFAULT NULL,
  `semestre_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `encadrement`
--

CREATE TABLE `encadrement` (
  `id` bigint(20) NOT NULL,
  `annee_universitaire` varchar(255) DEFAULT NULL,
  `date_debut` date DEFAULT NULL,
  `date_fin` date DEFAULT NULL,
  `enseignant_id` bigint(20) DEFAULT NULL,
  `etudiant_id` bigint(20) DEFAULT NULL,
  `memoire_id` bigint(20) DEFAULT NULL,
  `observation` varchar(1000) DEFAULT NULL,
  `statut` enum('EN_COURS','SUSPENDU','TERMINE') DEFAULT NULL,
  `sujet` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `enseignant`
--

CREATE TABLE `enseignant` (
  `id` bigint(20) NOT NULL,
  `adresse` varchar(255) DEFAULT NULL,
  `cin` varchar(255) DEFAULT NULL,
  `diplome` varchar(255) DEFAULT NULL,
  `filiere_id` bigint(20) DEFAULT NULL,
  `matiere_id` bigint(20) DEFAULT NULL,
  `matricule` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `specialite` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `etudiants`
--

CREATE TABLE `etudiants` (
  `id` bigint(20) NOT NULL,
  `adresse` varchar(255) DEFAULT NULL,
  `cin` varchar(255) DEFAULT NULL,
  `diplome` varchar(255) DEFAULT NULL,
  `domaine_id` bigint(20) DEFAULT NULL,
  `filiere_id` bigint(20) DEFAULT NULL,
  `matricule` varchar(255) DEFAULT NULL,
  `niveau_id` bigint(20) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `qr_code` longblob DEFAULT NULL,
  `releve` varchar(255) DEFAULT NULL,
  `type_formation` enum('CONTINUE','EN_LIGNE','PARTIELLE') DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `statut` enum('EN_ATTENTE','REFUSE','VALIDE') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `etudiants`
--

INSERT INTO `etudiants` (`id`, `adresse`, `cin`, `diplome`, `domaine_id`, `filiere_id`, `matricule`, `niveau_id`, `phone`, `photo`, `qr_code`, `releve`, `type_formation`, `user_id`, `statut`) VALUES
(1, 'Antananarivo', '123456789', 'eb85fa0d-844e-49d5-bfcf-52f6c52d2060_users.png', 1, 1, 'ETU001', 1, '0340000000', '014ac3a3-0272-4d25-8a8b-21ee1121faef_Maison du numerique.jfif', NULL, '7a9e9ece-4048-4715-ad44-dfb1a516e1c5_web.png', 'EN_LIGNE', 3, 'EN_ATTENTE');

-- --------------------------------------------------------

--
-- Structure de la table `fileres`
--

CREATE TABLE `fileres` (
  `id` bigint(20) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `niveau_id` bigint(20) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `fileres`
--

INSERT INTO `fileres` (`id`, `code`, `description`, `niveau_id`, `nom`) VALUES
(1, 'INFO', 'dnfjghfgjrhgjghrghfjvhmg', NULL, 'Informatique de gestion'),
(2, 'INFO', 'dnfjghfgjrhgjghrghfjvhmg', 1, 'Informatique de gestion');

-- --------------------------------------------------------

--
-- Structure de la table `histoires`
--

CREATE TABLE `histoires` (
  `id` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `lecons_progression`
--

CREATE TABLE `lecons_progression` (
  `id` bigint(20) NOT NULL,
  `cours_id` bigint(20) DEFAULT NULL,
  `date_terminaison` datetime(6) DEFAULT NULL,
  `etudiant_id` bigint(20) DEFAULT NULL,
  `lecon_id` bigint(20) DEFAULT NULL,
  `termine` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `leçon`
--

CREATE TABLE `leçon` (
  `id` bigint(20) NOT NULL,
  `contenu` text DEFAULT NULL,
  `duree` int(11) DEFAULT NULL,
  `titre` varchar(255) DEFAULT NULL,
  `chapitre_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `matieres`
--

CREATE TABLE `matieres` (
  `id` bigint(20) NOT NULL,
  `coefficient` int(11) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `semestre_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `memoire`
--

CREATE TABLE `memoire` (
  `id` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `domaine_id` bigint(20) DEFAULT NULL,
  `ensignant_id` bigint(20) DEFAULT NULL,
  `etudiant_id` bigint(20) DEFAULT NULL,
  `filiere_id` bigint(20) DEFAULT NULL,
  `livre` varchar(255) DEFAULT NULL,
  `niveau_id` bigint(20) DEFAULT NULL,
  `note_id` bigint(20) DEFAULT NULL,
  `statut` enum('ARCHIVE','BROUILLON','EN_COURS','PRET_A_SOUTENANCE','REFUSE','SOUMIS','SOUTENU','VALIDE') DEFAULT NULL,
  `theme` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `messages`
--

CREATE TABLE `messages` (
  `id` bigint(20) NOT NULL,
  `contenu` text DEFAULT NULL,
  `date_envoi` datetime(6) DEFAULT NULL,
  `lu` bit(1) NOT NULL,
  `conversation_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `moyennes`
--

CREATE TABLE `moyennes` (
  `id` bigint(20) NOT NULL,
  `etudiant_id` bigint(20) DEFAULT NULL,
  `mention` varchar(255) DEFAULT NULL,
  `moyenne_generale` double DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `niveaux`
--

CREATE TABLE `niveaux` (
  `id` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `niveaux`
--

INSERT INTO `niveaux` (`id`, `description`, `nom`) VALUES
(1, 'vndjfhjfdhhdghgjhfg', 'L3');

-- --------------------------------------------------------

--
-- Structure de la table `notes`
--

CREATE TABLE `notes` (
  `id` bigint(20) NOT NULL,
  `annees_univ_id` bigint(20) DEFAULT NULL,
  `domaine_id` bigint(20) DEFAULT NULL,
  `enseignant_id` bigint(20) DEFAULT NULL,
  `etudiant_id` bigint(20) DEFAULT NULL,
  `filiere_id` bigint(20) DEFAULT NULL,
  `matiere_id` bigint(20) DEFAULT NULL,
  `mention` varchar(255) DEFAULT NULL,
  `niveau_id` bigint(20) DEFAULT NULL,
  `semestre_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `valeur` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `notification`
--

CREATE TABLE `notification` (
  `id` bigint(20) NOT NULL,
  `date_creation` datetime(6) DEFAULT NULL,
  `etudiant_id` bigint(20) DEFAULT NULL,
  `lu` bit(1) NOT NULL,
  `message` varchar(255) DEFAULT NULL,
  `titre` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `organigrammes`
--

CREATE TABLE `organigrammes` (
  `id` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `titre` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `presences`
--

CREATE TABLE `presences` (
  `id` bigint(20) NOT NULL,
  `date_presence` date DEFAULT NULL,
  `emploi_du_temps_id` bigint(20) DEFAULT NULL,
  `etudiant_id` bigint(20) DEFAULT NULL,
  `heure_presence` time(6) DEFAULT NULL,
  `remarque` varchar(255) DEFAULT NULL,
  `statut` enum('ABSENT','JUSTIFIE','PRESENT','RETARD') DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `progressions`
--

CREATE TABLE `progressions` (
  `id` bigint(20) NOT NULL,
  `cours_id` bigint(20) DEFAULT NULL,
  `date_debut` datetime(6) DEFAULT NULL,
  `derniere_activite` datetime(6) DEFAULT NULL,
  `derniere_lecon_id` bigint(20) DEFAULT NULL,
  `etudiant_id` bigint(20) DEFAULT NULL,
  `nombre_lecons_terminees` int(11) DEFAULT NULL,
  `nombre_total_lecons` int(11) DEFAULT NULL,
  `pourcentage` double DEFAULT NULL,
  `statut` enum('COMMENCE','EN_COURS','TERMINE') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `question`
--

CREATE TABLE `question` (
  `id` bigint(20) NOT NULL,
  `contenu` varchar(255) DEFAULT NULL,
  `points` int(11) DEFAULT NULL,
  `quiz_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `quiz`
--

CREATE TABLE `quiz` (
  `id` bigint(20) NOT NULL,
  `cours_id` bigint(20) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `duree` int(11) DEFAULT NULL,
  `nombre_question` int(11) DEFAULT NULL,
  `titre` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `reponse`
--

CREATE TABLE `reponse` (
  `id` bigint(20) NOT NULL,
  `contenu` varchar(255) DEFAULT NULL,
  `correcte` bit(1) DEFAULT NULL,
  `question_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `ressources`
--

CREATE TABLE `ressources` (
  `id` bigint(20) NOT NULL,
  `date_ajout` date DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `taille` double DEFAULT NULL,
  `type` enum('DOCUMENT','IMAGE','LIEN','PDF','VIDEO') DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `leçon_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `semestres`
--

CREATE TABLE `semestres` (
  `id` bigint(20) NOT NULL,
  `actif` bit(1) NOT NULL,
  `annees_univ_id` bigint(20) DEFAULT NULL,
  `date_debut` date DEFAULT NULL,
  `date_fin` date DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `numero` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `slide`
--

CREATE TABLE `slide` (
  `id` bigint(20) NOT NULL,
  `active` bit(1) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `titre` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `soumissions`
--

CREATE TABLE `soumissions` (
  `id` bigint(20) NOT NULL,
  `commentaire` varchar(255) DEFAULT NULL,
  `date_soumission` datetime(6) DEFAULT NULL,
  `etudiant_id` bigint(20) DEFAULT NULL,
  `fichier` varchar(255) DEFAULT NULL,
  `note` double DEFAULT NULL,
  `devoir_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`id`, `email`, `password`, `role`, `username`) VALUES
(1, 'superadmin@gmail.com', '$2a$10$nGHmuq5PHEYCay7TBIDs4utbGhw9DNICtwocM3o.U0HdEFp6W9Vuy', 'SUPER_ADMIN', 'superadmin'),
(2, 'admin1@gmail.com', '$2a$10$P1G3bgOdqnwz16VVZ9zox.DnaiQOrSkQBr2qdZtXXhXLwyQ8pOhla', 'ADMIN', 'admin1'),
(3, 'flavien@gmail.com', '$2a$10$.BUy7tMgfCd0Dn9nYceKYeSdSE4jbiNjUYHAyaMfZWC1LmT6SAU8u', 'ETUDIANT', 'mihaja');

-- --------------------------------------------------------

--
-- Structure de la table `videos`
--

CREATE TABLE `videos` (
  `id` bigint(20) NOT NULL,
  `duree` int(11) DEFAULT NULL,
  `titre` varchar(255) DEFAULT NULL,
  `url_video` varchar(255) DEFAULT NULL,
  `leçon_id` bigint(20) DEFAULT NULL,
  `type_video` enum('UPLOAD','YOUTUBE') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `actualites`
--
ALTER TABLE `actualites`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `admins`
--
ALTER TABLE `admins`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `annees_univ`
--
ALTER TABLE `annees_univ`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `chapitres`
--
ALTER TABLE `chapitres`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK4uwea8ecrlwf13pqwdml1g27a` (`cours_id`);

--
-- Index pour la table `conversations`
--
ALTER TABLE `conversations`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `cours`
--
ALTER TABLE `cours`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `devoirs`
--
ALTER TABLE `devoirs`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `documents`
--
ALTER TABLE `documents`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKl74rl6l4s41aw79ole2i44tex` (`leçon_id`);

--
-- Index pour la table `domaine`
--
ALTER TABLE `domaine`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `emploi_du_temps`
--
ALTER TABLE `emploi_du_temps`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `encadrement`
--
ALTER TABLE `encadrement`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `enseignant`
--
ALTER TABLE `enseignant`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `etudiants`
--
ALTER TABLE `etudiants`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK7dcho6ioi2rdfjm2fjtsm1xcy` (`matricule`);

--
-- Index pour la table `fileres`
--
ALTER TABLE `fileres`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `histoires`
--
ALTER TABLE `histoires`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `lecons_progression`
--
ALTER TABLE `lecons_progression`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `leçon`
--
ALTER TABLE `leçon`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK1g31s65b21r68r2sivl41j6sx` (`chapitre_id`);

--
-- Index pour la table `matieres`
--
ALTER TABLE `matieres`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `memoire`
--
ALTER TABLE `memoire`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `messages`
--
ALTER TABLE `messages`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKt492th6wsovh1nush5yl5jj8e` (`conversation_id`);

--
-- Index pour la table `moyennes`
--
ALTER TABLE `moyennes`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `niveaux`
--
ALTER TABLE `niveaux`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `notes`
--
ALTER TABLE `notes`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `notification`
--
ALTER TABLE `notification`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `organigrammes`
--
ALTER TABLE `organigrammes`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `presences`
--
ALTER TABLE `presences`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `progressions`
--
ALTER TABLE `progressions`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `question`
--
ALTER TABLE `question`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKb0yh0c1qaxfwlcnwo9dms2txf` (`quiz_id`);

--
-- Index pour la table `quiz`
--
ALTER TABLE `quiz`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `reponse`
--
ALTER TABLE `reponse`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKp0yq2hox7fyw8rpbhx9t514rt` (`question_id`);

--
-- Index pour la table `ressources`
--
ALTER TABLE `ressources`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKgxuoevwx69jntlheloygcjf9` (`leçon_id`);

--
-- Index pour la table `semestres`
--
ALTER TABLE `semestres`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `slide`
--
ALTER TABLE `slide`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `soumissions`
--
ALTER TABLE `soumissions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKodxkveajnkiu6ag8kuah7y6oy` (`devoir_id`);

--
-- Index pour la table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `videos`
--
ALTER TABLE `videos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKqx4hx9k3ar16v2ue9x39ovo81` (`leçon_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `actualites`
--
ALTER TABLE `actualites`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `admins`
--
ALTER TABLE `admins`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `annees_univ`
--
ALTER TABLE `annees_univ`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `chapitres`
--
ALTER TABLE `chapitres`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `conversations`
--
ALTER TABLE `conversations`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `cours`
--
ALTER TABLE `cours`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `devoirs`
--
ALTER TABLE `devoirs`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `documents`
--
ALTER TABLE `documents`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `domaine`
--
ALTER TABLE `domaine`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `emploi_du_temps`
--
ALTER TABLE `emploi_du_temps`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `encadrement`
--
ALTER TABLE `encadrement`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `enseignant`
--
ALTER TABLE `enseignant`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `etudiants`
--
ALTER TABLE `etudiants`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `fileres`
--
ALTER TABLE `fileres`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `histoires`
--
ALTER TABLE `histoires`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `lecons_progression`
--
ALTER TABLE `lecons_progression`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `leçon`
--
ALTER TABLE `leçon`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `matieres`
--
ALTER TABLE `matieres`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `memoire`
--
ALTER TABLE `memoire`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `messages`
--
ALTER TABLE `messages`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `moyennes`
--
ALTER TABLE `moyennes`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `niveaux`
--
ALTER TABLE `niveaux`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `notes`
--
ALTER TABLE `notes`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `notification`
--
ALTER TABLE `notification`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `organigrammes`
--
ALTER TABLE `organigrammes`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `presences`
--
ALTER TABLE `presences`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `progressions`
--
ALTER TABLE `progressions`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `question`
--
ALTER TABLE `question`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `quiz`
--
ALTER TABLE `quiz`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `reponse`
--
ALTER TABLE `reponse`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `ressources`
--
ALTER TABLE `ressources`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `semestres`
--
ALTER TABLE `semestres`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `slide`
--
ALTER TABLE `slide`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `soumissions`
--
ALTER TABLE `soumissions`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `videos`
--
ALTER TABLE `videos`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `chapitres`
--
ALTER TABLE `chapitres`
  ADD CONSTRAINT `FK4uwea8ecrlwf13pqwdml1g27a` FOREIGN KEY (`cours_id`) REFERENCES `cours` (`id`);

--
-- Contraintes pour la table `documents`
--
ALTER TABLE `documents`
  ADD CONSTRAINT `FKl74rl6l4s41aw79ole2i44tex` FOREIGN KEY (`leçon_id`) REFERENCES `leçon` (`id`);

--
-- Contraintes pour la table `leçon`
--
ALTER TABLE `leçon`
  ADD CONSTRAINT `FK1g31s65b21r68r2sivl41j6sx` FOREIGN KEY (`chapitre_id`) REFERENCES `chapitres` (`id`);

--
-- Contraintes pour la table `messages`
--
ALTER TABLE `messages`
  ADD CONSTRAINT `FKt492th6wsovh1nush5yl5jj8e` FOREIGN KEY (`conversation_id`) REFERENCES `conversations` (`id`);

--
-- Contraintes pour la table `question`
--
ALTER TABLE `question`
  ADD CONSTRAINT `FKb0yh0c1qaxfwlcnwo9dms2txf` FOREIGN KEY (`quiz_id`) REFERENCES `quiz` (`id`);

--
-- Contraintes pour la table `reponse`
--
ALTER TABLE `reponse`
  ADD CONSTRAINT `FKp0yq2hox7fyw8rpbhx9t514rt` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`);

--
-- Contraintes pour la table `ressources`
--
ALTER TABLE `ressources`
  ADD CONSTRAINT `FKgxuoevwx69jntlheloygcjf9` FOREIGN KEY (`leçon_id`) REFERENCES `leçon` (`id`);

--
-- Contraintes pour la table `soumissions`
--
ALTER TABLE `soumissions`
  ADD CONSTRAINT `FKodxkveajnkiu6ag8kuah7y6oy` FOREIGN KEY (`devoir_id`) REFERENCES `devoirs` (`id`);

--
-- Contraintes pour la table `videos`
--
ALTER TABLE `videos`
  ADD CONSTRAINT `FKqx4hx9k3ar16v2ue9x39ovo81` FOREIGN KEY (`le��on_id`) REFERENCES `le��on` (`id`);

--
-- Tables pour le service VISIO (conférence temps réel)
--

CREATE TABLE IF NOT EXISTS `salle_visio` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code_acces` varchar(20) NOT NULL UNIQUE,
  `titre` varchar(255) DEFAULT NULL,
  `description` varchar(3000) DEFAULT NULL,
  `enseignant_id` bigint DEFAULT NULL,
  `matiere_id` bigint DEFAULT NULL,
  `cours_id` bigint DEFAULT NULL,
  `date_debut` datetime(6) DEFAULT NULL,
  `date_fin` datetime(6) DEFAULT NULL,
  `statut` enum('PLANIFIEE','EN_COURS','TERMINEE') DEFAULT 'PLANIFIEE',
  `lien_connexion` varchar(500) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `participant_visio` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `salle_visio_id` bigint NOT NULL,
  `utilisateur_id` bigint DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `role` enum('ENSEIGNANT','ETUDIANT') DEFAULT NULL,
  `session_id` varchar(255) DEFAULT NULL,
  `actif` bit(1) DEFAULT b'1',
  `heure_connexion` datetime(6) DEFAULT NULL,
  `heure_deconnexion` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_participant_salle` (`salle_visio_id`),
  CONSTRAINT `FK_participant_salle` FOREIGN KEY (`salle_visio_id`) REFERENCES `salle_visio` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `chat_visio` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `salle_visio_id` bigint NOT NULL,
  `utilisateur_id` bigint DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `message` varchar(2000) DEFAULT NULL,
  `date_envoi` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_chat_salle` (`salle_visio_id`),
  CONSTRAINT `FK_chat_salle` FOREIGN KEY (`salle_visio_id`) REFERENCES `salle_visio` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

package com.email.email.service;


import com.email.email.dto.EmailRequest;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendEmail(EmailRequest request) throws Exception {

        MimeMessage message =
                mailSender.createMimeMessage();

        MimeMessageHelper helper =
                new MimeMessageHelper(
                        message,
                        true
                );

        // Expéditeur
        helper.setFrom("flavienrandria81@gmail.com");

        // Destinataire
        helper.setTo(request.getTo());

        // Sujet
        helper.setSubject(request.getSubject());

        // Contenu
        helper.setText(request.getMessage(), true);

        // Pièce jointe
        if (request.getAttachment() != null
                && request.getFileName() != null) {

            helper.addAttachment(
                    request.getFileName(),
                    new ByteArrayResource(request.getAttachment())
            );
        }

        // ======= ICI =======
        try {

            mailSender.send(message);

            System.out.println("Email envoyé avec succès");

        } catch (Exception e) {

            System.out.println("Erreur lors de l'envoi du mail :");

            e.printStackTrace();

            throw e;
        }
    }
}
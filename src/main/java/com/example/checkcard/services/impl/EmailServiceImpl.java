package com.example.checkcard.services.impl;

import com.example.checkcard.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;

    @Override
    public void sendBadgeByEmail(
            String to,
            String nom,
            byte[] pdf
    ) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper =
                new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject("Votre carte étudiant ISM");
        helper.setText(
                "Bonjour " + nom + ",\n\n" +
                        "Veuillez trouver ci-joint votre carte étudiant.\n\n" +
                        "ISM",
                false
        );

        helper.addAttachment(
                "badge-etudiant.pdf",
                new ByteArrayResource(pdf)
        );

        mailSender.send(message);
    }
}

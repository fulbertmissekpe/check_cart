package com.example.checkcard.services;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendBadgeByEmail(
            String to,
            String nom,
            byte[] pdf
    ) throws MessagingException;
}

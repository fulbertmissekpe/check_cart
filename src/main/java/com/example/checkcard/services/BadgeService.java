package com.example.checkcard.services;

public interface BadgeService {
    byte[] generateBadge(
            String nom,
            String matricule,
            String classe,
            byte[] qrCode,
            byte[] logo
    ) throws Exception;
}

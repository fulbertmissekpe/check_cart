package com.example.checkcard.data.enums;

public enum Role {
    ADMIN,
    VIGIL;
    public static Role getRole(String nomRole) {
        String nom = nomRole.toLowerCase();

        if (nom.equalsIgnoreCase("admin")) {
            return Role.ADMIN;
        }

        if (nom.equalsIgnoreCase("vigil")) {
            return Role.VIGIL;
        }

        return null;
    }
}

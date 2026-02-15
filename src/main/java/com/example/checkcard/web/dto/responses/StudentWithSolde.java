package com.example.checkcard.web.dto.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StudentWithSolde {
    private String id;
    private String matricule;
    private String email;
    private String nomComplet;
    private String classe;
    private double amount;
}

package com.example.checkcard.web.dto.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SoldeDto {
    private String id;
    private String matricule;
    private String nomComplet;
    private String classe;
    private String ecole;
    private double amount;
}

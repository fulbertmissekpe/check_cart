package com.example.checkcard.web.dto.responses;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {
    private String id;
    private String matricule;
    private String email;
    private String nomComplet;
    private String classe;
}

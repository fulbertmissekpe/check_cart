package com.example.checkcard.utils.mappers;

import com.example.checkcard.data.entities.Solde;
import com.example.checkcard.data.entities.Student;
import com.example.checkcard.web.dto.responses.StudentWithSolde;

public class StudentWithSoldeMapper {
    public static StudentWithSolde toDto(Solde solde, Student student) {
        return StudentWithSolde.builder()
                .id(student.getId())
                .matricule(student.getMatricule())
                .email(student.getEmail())
                .nomComplet(student.getNomComplet())
                .classe(student.getClasse())
                .amount(solde.getAmount())
                .build();
    }
}

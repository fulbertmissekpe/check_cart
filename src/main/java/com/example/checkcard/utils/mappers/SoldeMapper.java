package com.example.checkcard.utils.mappers;

import com.example.checkcard.data.entities.Solde;
import com.example.checkcard.web.dto.responses.SoldeDto;
import org.apache.poi.ss.usermodel.Row;

import static com.example.checkcard.utils.mappers.Tools.getCellValue;

public class SoldeMapper {
    public static Solde rowToEntity(Row row, String ecole) {
        return Solde.builder()
                .matricule(getCellValue(row.getCell(1)))
                .amount(Double.parseDouble(getCellValue(row.getCell(3))))
                .nomComplet(getCellValue(row.getCell(0)))
                .classe(getCellValue(row.getCell(2)))
                .ecole(ecole)
                .build();
    }

    public static SoldeDto toDto(Solde solde) {
        return  SoldeDto.builder()
                .id(solde.getId())
                .nomComplet(solde.getNomComplet())
                .matricule(solde.getMatricule())
                .ecole(solde.getEcole())
                .classe(solde.getClasse())
                .amount(solde.getAmount())
                .build();
    }

}

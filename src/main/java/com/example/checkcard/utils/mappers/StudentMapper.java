package com.example.checkcard.utils.mappers;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import com.example.checkcard.data.entities.Student;
import com.example.checkcard.web.dto.responses.StudentDto;

import static com.example.checkcard.utils.mappers.Tools.getCellValue;


public class StudentMapper {
    public static StudentDto toDto(Student student) {
        return StudentDto.builder()
                .id(student.getId())
                .matricule(student.getMatricule())
                .email(student.getEmail())
                .nomComplet(student.getNomComplet())
                .classe(student.getClasse())
                .build();
    }
    public static Student rowToEntity(Row row, String ecole) {
        return Student.builder()
                .matricule(getCellValue(row.getCell(1)))
                .nomComplet(getCellValue(row.getCell(3)) +" "+getCellValue(row.getCell(4)))
                .classe(getCellValue(row.getCell(0)))
                .email(getCellValue(row.getCell(15)))
                .ecole(ecole)
                .build();

    }
    public static Student toEntity(StudentDto studentDto) {
        return Student.builder()
                .matricule(studentDto.getMatricule())
                .email(studentDto.getEmail())
                .nomComplet(studentDto.getNomComplet())
                .classe(studentDto.getClasse())
                .build();
    }


}

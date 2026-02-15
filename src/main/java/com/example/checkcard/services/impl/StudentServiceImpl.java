package com.example.checkcard.services.impl;

import com.example.checkcard.data.entities.Historical;
import com.example.checkcard.data.entities.Solde;
import com.example.checkcard.data.entities.Student;
import com.example.checkcard.data.enums.TypeHistorical;
import com.example.checkcard.data.repositories.HistoricalRepository;
import com.example.checkcard.data.repositories.SoldeRepository;
import com.example.checkcard.data.repositories.StudentRepository;
import com.example.checkcard.services.*;
import com.example.checkcard.utils.mappers.StudentMapper;
import com.example.checkcard.utils.mappers.StudentWithSoldeMapper;
import com.example.checkcard.web.dto.responses.StudentWithSolde;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

import static com.example.checkcard.utils.mappers.Tools.getCellValue;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final SoldeRepository soldeRepository;
    private final QrCodeService qrCodeService;
    private final BadgeService badgeService;
    private final EmailService emailService;
    private final HistoricalRepository historicalRepository;
    @Override
    public Map<String, Object> saveAll(MultipartFile studentsFile, String ecole) {
        List<Student> students = new ArrayList<>();
        HashMap<String, Object> response = new HashMap<>();
        String fileName = studentsFile.getOriginalFilename();
        try (InputStream inputStream = studentsFile.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;
                Cell firstCell = row.getCell(0);
                if (firstCell == null || getCellValue(firstCell).isEmpty()) {
                    continue;
                }
                Student student = StudentMapper.rowToEntity(row, ecole);
                students.add(student);
            }
            if (studentRepository.findByEcole(ecole) != null) studentRepository.deleteAllByEcole(ecole);
            //List<Student> studentsToSave = sendEmail(students);
             studentRepository.saveAll(students);
            workbook.close();
            response.put("message", "Successfully uploaded: " + fileName);
            historicalRepository.save(Historical.builder()
                    .type(TypeHistorical.ELEVES)
                    .date(new Date())
                    .fileName(fileName)
                    .build());
        } catch (Exception e) {
            response.put("message", e.getMessage());
        }
        return response;
    }

    @Override
    public Map<String, Object> getAll() {
        return Map.of();
    }

    @Override
    public Map<String, Object> getStudent(String id) {
        return Map.of();
    }

    @Override
    public Map<String, Object> checkStudent(String matricule) {
        Map<String, Object> response = new HashMap<>();
        List<Solde> soldes = soldeRepository.findAllByMatricule(matricule);
        List<Student> students = studentRepository.findAllByMatricule(matricule);
        response.put("message", "ok");
        if (soldes.isEmpty()|| students.isEmpty()) {
            response.put("message", "Student not found ");
            return response;
        }
        Solde solde = soldes.stream().filter(s -> s.getAmount() != 0).findFirst().orElse(null);
        if (solde == null) {
            response.put("message", "Student not found ");
            return response;
        }
        Student student = students.stream().filter(s -> s.getMatricule().equals(solde.getMatricule())).findFirst().orElse(null);
        if (student == null) {
            response.put("message", "Student not found ");
            return response;
        }
        StudentWithSolde studentWithSolde = StudentWithSoldeMapper.toDto(solde, student);
        response.put("message", "Student found");
        response.put("student", studentWithSolde);
        return response;
    }
    private List<Student> sendEmail(List<Student> students) {
        students.forEach(
                s -> {
                    if (s.getCard() == null);{
                        byte[] qr = null;
                        try {
                            qr = qrCodeService.generateQrCode(s.getMatricule());
                            ClassPathResource resource = new ClassPathResource("static/images/LOGO-ISM-SENEGAL.jpg");
                            byte[] logo = resource.getInputStream().readAllBytes();
                            byte[] pdf = badgeService.generateBadge(
                                    s.getNomComplet(),
                                    s.getMatricule(),
                                    s.getClasse(),
                                    qr,
                                    logo
                            );
                            emailService.sendBadgeByEmail(
                                    s.getEmail(),
                                    s.getNomComplet(),
                                    pdf
                            );
                            s.setCard(qr);
                        }
                        catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        );
        return students;
    }
}

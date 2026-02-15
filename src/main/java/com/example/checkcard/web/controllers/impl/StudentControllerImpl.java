package com.example.checkcard.web.controllers.impl;

import com.example.checkcard.services.StudentService;
import com.example.checkcard.web.controllers.StudentController;
import com.example.checkcard.web.dto.responses.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import static com.example.checkcard.utils.mappers.Tools.getMapResponseEntity;

@RequiredArgsConstructor
@RestController
public class StudentControllerImpl implements StudentController {
    private final StudentService studentService;
    @Override
    public ResponseEntity<Map<String, Object>> getAllStudents() {
        return null;
    }

    @Override
    public ResponseEntity<Map<String, Object>> check(String matricule) {
        Map<String, Object> response = studentService.checkStudent(matricule);
        if (response.get("message").equals("Student found")) {
            return new ResponseEntity<>(RestResponse.response(HttpStatus.OK, response.get("student"), "Student"), HttpStatus.OK);
        }
        return new ResponseEntity<>(RestResponse.response(HttpStatus.NOT_FOUND, response.get("message"), "Student"), HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Map<String, Object>> saveStudents(MultipartFile students, String ecole) {
        Map<String, Object> response = studentService.saveAll(students, ecole);
        return getMapResponseEntity(response);
    }
}

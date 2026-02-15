package com.example.checkcard.web.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;


@RequestMapping("api/v1/students")
public interface StudentController {
    @GetMapping
    ResponseEntity<Map<String, Object>> getAllStudents();
    @GetMapping("/check")
    ResponseEntity<Map<String, Object>> check(@RequestParam("matricule") String id);
    @PostMapping(value = "", consumes = MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Map<String, Object>> saveStudents( @RequestParam("file") MultipartFile students, @RequestParam("ecole") String ecole);
}

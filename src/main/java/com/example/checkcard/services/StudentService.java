package com.example.checkcard.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;


public interface StudentService {
   Map<String, Object> saveAll(MultipartFile students, String ecole);
   Map<String, Object> getAll();
   Map<String, Object> getStudent(String id);
   Map<String, Object> checkStudent(String matricule);

}

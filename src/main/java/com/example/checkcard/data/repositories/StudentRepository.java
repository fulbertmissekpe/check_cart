package com.example.checkcard.data.repositories;

import com.example.checkcard.data.entities.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends MongoRepository<Student, String> {
    Student getByMatricule(String matricule);

    List<Student> findAllByMatricule(String matricule);

    Student findByEcole(String ecole);

    void deleteAllByEcole(String ecole);
}

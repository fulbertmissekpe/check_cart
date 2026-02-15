package com.example.checkcard.data.repositories;

import com.example.checkcard.data.entities.Solde;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SoldeRepository extends MongoRepository<Solde, String> {

    List<Solde> findAllByMatricule(String matricule);

    Solde findByEcole(String ecole);

    void deleteAllByEcole(String ecole);
}

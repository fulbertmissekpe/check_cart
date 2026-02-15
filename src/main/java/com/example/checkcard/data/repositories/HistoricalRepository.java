package com.example.checkcard.data.repositories;

import com.example.checkcard.data.entities.Historical;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HistoricalRepository extends MongoRepository<Historical, String> {
}

package com.example.checkcard.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class cc {

    @Value("${spring.data.mongodb.uri:NOT_FOUND}")
    private String uri;
    
    @PostConstruct
    public void checkMongo() {
        System.out.println("Mongo URI = " + uri);
    }
    
}

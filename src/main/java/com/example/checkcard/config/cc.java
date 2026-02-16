package com.example.checkcard.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class cc {

    @Value("${MONGO_URI:NOT_FOUND}")
    private String mongoUri;

    @PostConstruct
    public void print() {
        System.out.println(">>> MONGO_URI = " + mongoUri);
    }
}

package com.example.checkcard.web.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping("/api/v1/historicals")
public interface HistoricalController {
    @GetMapping
    ResponseEntity<Map<String, Object>> getAll();
}

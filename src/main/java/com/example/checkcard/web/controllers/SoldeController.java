package com.example.checkcard.web.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RequestMapping("/api/v1/soldes")
public interface SoldeController {
    @GetMapping
    ResponseEntity<Map<String, Object>> getAllSoldes();
    @GetMapping("/pagination")
    ResponseEntity<Map<String, Object>> getAllWithPagination(@RequestParam("page") int page, @RequestParam("size") int size,
                                                               @RequestParam(value = "search", required = false) String search,
                                                               @RequestParam(value = "school", required = false) String school,
                                                               @RequestParam(value = "year", required = false) String year,
                                                               @RequestParam(value = "classe", required = false) String classe);
    @GetMapping("/filter-options")
    ResponseEntity<Map<String, Object>> getFilterOptions();
    @PostMapping
    ResponseEntity<Map<String, Object>> saveSoldes(@RequestParam("file") MultipartFile soldesFile, @RequestParam("ecole") String ecole);
}

package com.example.checkcard.web.controllers;

import com.example.checkcard.web.dto.requests.Login;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public interface AuthController {
    @PostMapping("/login")
    ResponseEntity<Map<String, Object>> login(@Valid @RequestBody Login login);

    @GetMapping("/me")
    ResponseEntity<Map<String, Object>> me(@CookieValue(name = "jwt", required = false) String token);

    @PostMapping("/logout")
    ResponseEntity<Map<String, Object>> logout();
}

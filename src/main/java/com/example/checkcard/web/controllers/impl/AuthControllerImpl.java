package com.example.checkcard.web.controllers.impl;

import com.example.checkcard.data.entities.User;
import com.example.checkcard.services.UserService;
import com.example.checkcard.utils.JwtTools;
import com.example.checkcard.web.controllers.AuthController;
import com.example.checkcard.web.dto.requests.Login;
import com.example.checkcard.web.dto.responses.RestResponse;
import com.example.checkcard.web.dto.responses.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final UserService userService;
    private final JwtTools  jwtTools;
    @Value("${application.security.jwt.expiration-ms}")
    private long expiration;
    @Override
    public ResponseEntity<Map<String, Object>> login(Login login) {
        Map<String, Object> response = userService.check(login);
        if (response.get("message").equals("User found")) {
            String token = response.get("token").toString();
            ResponseCookie cookie = generateCookie(token);
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(RestResponse.response(HttpStatus.OK, response.get("UserResponse"), "Authenticate"));        }
       return new ResponseEntity<>(RestResponse.response(HttpStatus.BAD_REQUEST, response.get("message"), "User not found"), HttpStatus.BAD_REQUEST);
    }

    private ResponseCookie generateCookie(String token) {
        return ResponseCookie.from("jwt", token)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(expiration)
                .build();
    }

    @Override
    public ResponseEntity<Map<String, Object>> me(String token) {
        if (token == null || token.isEmpty()) {
            return new ResponseEntity<>(RestResponse.response(HttpStatus.UNAUTHORIZED, null, "Not authenticated"), HttpStatus.UNAUTHORIZED);
        }
        UserDto user = userService.getUserFromToken(token);
        if (user == null) {
            return new ResponseEntity<>(RestResponse.response(HttpStatus.UNAUTHORIZED, null, "Invalid token"), HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(RestResponse.response(HttpStatus.OK, user, "User found"));
    }

    @Override
    public ResponseEntity<Map<String, Object>> logout() {
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(0)
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(RestResponse.response(HttpStatus.OK, null, "Logged out successfully"));
    }
}

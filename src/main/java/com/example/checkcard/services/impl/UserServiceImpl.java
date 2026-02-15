package com.example.checkcard.services.impl;

import com.example.checkcard.data.entities.User;
import com.example.checkcard.data.repositories.UserRepository;
import com.example.checkcard.services.UserService;
import com.example.checkcard.utils.JwtTools;
import com.example.checkcard.utils.mappers.UserMapper;
import com.example.checkcard.web.dto.requests.Login;
import com.example.checkcard.web.dto.responses.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTools jwtTools;

    @Override
    public Map<String, Object> check(Login login) {
        Map<String, Object> response = new HashMap<>();
        User user = userRepository.findByEmail(login.getEmail()).orElse(null);
        if (user != null && passwordEncoder.matches(login.getPassword(), user.getPassword())) {
            String token = jwtTools.generateToken(user);
            UserDto userDto = UserMapper.toDto(user);
            response.put("message", "User found");
            response.put("UserResponse", userDto);
            response.put("token", token);
            return response;
        }
        response.put("message", "Email or password incorrect");
        return response;
    }

    @Override
    public UserDto getUserFromToken(String token) {
        try {
            String email = jwtTools.extractEmail(token);
            User user = userRepository.findByEmail(email).orElse(null);
            return user != null ? UserMapper.toDto(user) : null;
        } catch (Exception e) {
            return null;
        }
    }
}

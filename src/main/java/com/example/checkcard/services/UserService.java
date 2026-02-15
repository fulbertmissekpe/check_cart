package com.example.checkcard.services;

import com.example.checkcard.web.dto.requests.Login;
import com.example.checkcard.web.dto.responses.UserDto;

import java.util.Map;


public interface UserService {
    Map<String, Object> check(Login login);
    UserDto getUserFromToken(String token);
}

package com.example.checkcard.utils.mappers;

import com.example.checkcard.data.entities.User;
import com.example.checkcard.web.dto.responses.UserDto;

public class UserMapper {
    public static UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}

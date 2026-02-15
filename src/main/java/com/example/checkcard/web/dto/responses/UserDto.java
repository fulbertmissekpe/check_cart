package com.example.checkcard.web.dto.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserDto {
    private String id;
    private  String name;
    private String email;
    private String role;
}

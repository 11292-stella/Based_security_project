package com.example.Based_security_project.Dto;

import com.example.Based_security_project.Model.User;
import lombok.Data;

@Data
public class UserResponseDto {
    private int id;
    private String username;
    private String email;

    public UserResponseDto(User user) {
        id = user.getId();
        username = user.getUsername();
        email = user.getEmail();
    }
}

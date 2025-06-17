package com.example.Based_security_project.Dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserDto {
    @NotEmpty(message = "Il nome non può essere vuoto")
    private String nome;

    @NotEmpty(message = "Il cognome non può essere vuoto")
    private String cognome;

    @NotEmpty(message = "Lo username non può essere vuoto")
    private String username;

    @NotEmpty(message = "La password non può essere vuota")
    private String password;

    @NotEmpty(message = "La password non può essere vuota")
    private String email;
}

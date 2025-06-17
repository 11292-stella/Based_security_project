package com.example.Based_security_project.controller;

import com.example.Based_security_project.model.User;
import com.example.Based_security_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users") // Aggiungi questa annotazione
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')") // Proteggi l'endpoint
    public List<User> getAllUsers() {
        return userService.getAllUser();
    }

    
}

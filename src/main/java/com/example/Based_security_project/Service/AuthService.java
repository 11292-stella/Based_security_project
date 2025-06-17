package com.example.Based_security_project.Service;

import com.example.Based_security_project.Dto.LoginDto;
import com.example.Based_security_project.Dto.LoginResponseDto;
import com.example.Based_security_project.Dto.UserDto;
import com.example.Based_security_project.Enumeration.Role;
import com.example.Based_security_project.Exception.NotFoundException;
import com.example.Based_security_project.Model.User;
import com.example.Based_security_project.Repository.UserRepository;
import com.example.Based_security_project.Security.JwtTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTool jwtTool;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    public LoginResponseDto login(LoginDto loginDto) throws NotFoundException {
        User user = userRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new NotFoundException("Utente con questo username/password non trovato"));

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new NotFoundException("Utente con questo username/password non trovato");
        }

        String token = jwtTool.createToken(user);
        LoginResponseDto response = new LoginResponseDto();
        response.setToken(token);
        return response;
    }

    public User register(UserDto userDto) {
        // Puoi aggiungere qui eventuali controlli, es. username già esistente

        User user = new User();
        user.setNome(userDto.getNome());
        user.setCognome(userDto.getCognome());
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(Role.USER); // ruolo di default

        User savedUser = userRepository.save(user);
        emailService.sendWelcomeEmail(savedUser);
        return savedUser;
    }
}

package com.example.Based_security_project.Controller;

import com.example.Based_security_project.Dto.LoginDto;
import com.example.Based_security_project.Dto.LoginResponseDto;
import com.example.Based_security_project.Dto.UserDto;
import com.example.Based_security_project.Dto.UserResponseDto;
import com.example.Based_security_project.Exception.NotFoundException;
import com.example.Based_security_project.Exception.ValidationException;
import com.example.Based_security_project.Model.User;
import com.example.Based_security_project.Service.AuthService;
import com.example.Based_security_project.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class AuthController {
    private AuthService authService;
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody UserDto userDto) {
        User user = userService.saveUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponseDto(user));
    }

    @PostMapping("/auth/login")
    public LoginResponseDto login(@RequestBody @Validated LoginDto loginDto, BindingResult bindingResult) throws ValidationException, NotFoundException {

        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .reduce("", (s,e) -> s + e));
        }

        return authService.login(loginDto); // ora torna LoginResponseDto
    }
}

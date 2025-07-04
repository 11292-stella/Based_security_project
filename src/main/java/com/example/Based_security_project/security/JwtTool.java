package com.example.Based_security_project.security;


import com.example.Based_security_project.exception.NotFoundException;
import com.example.Based_security_project.model.User;
import com.example.Based_security_project.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class JwtTool {
    @Value("${jwt.duration}")
    private long durata;

    @Value("${jwt.secret}")
    private String chiaveSegreta;

    @Autowired
    private UserService userService;

    public String createToken(User user) {
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + durata))
                .subject(String.valueOf(user.getId()))
                .signWith(Keys.hmacShaKeyFor(chiaveSegreta.getBytes()))
                .compact();
    }

    public void validateToken(String token) {
        Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(chiaveSegreta.getBytes()))
                .build()
                .parse(token);
    }

    public User getUserFromToken(String token) throws NotFoundException {

        String subject = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(chiaveSegreta.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();


        if (subject == null || !subject.matches("\\d+")) {
            throw new NotFoundException("Invalid user ID in token.");
        }

        int id = Integer.parseInt(subject);
        return userService.getUser(id);
    }
}

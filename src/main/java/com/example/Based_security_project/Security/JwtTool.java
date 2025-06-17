package com.example.Based_security_project.Security;


import com.example.Based_security_project.Exception.NotFoundException;
import com.example.Based_security_project.Model.User;
import com.example.Based_security_project.Service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
@PropertySource("classpath:env.properties")
public class JwtTool {

    @Value("${jwt.duration}")
    private long durata;

    @Value("${jwt.secret}")
    private String chiaveSegreta;

    @Autowired
    private UserService userService;

    public String createToken(User user) {
        return Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + durata))
                .signWith(Keys.hmacShaKeyFor(chiaveSegreta.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public void validateToken(String token) {
        Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(chiaveSegreta.getBytes()))
                .build()
                .parseClaimsJws(token); // se non è valido, lancia eccezione
    }

    public User getUserFromToken(String token) throws NotFoundException {
        String subject = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(chiaveSegreta.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        int userId = Integer.parseInt(subject);
        return userService.getUser(userId);
    }


}

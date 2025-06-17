package com.example.Based_security_project.Service;

import com.example.Based_security_project.Model.User;
import lombok.Data;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Data
public class EmailService {

    private JavaMailSender mailSender;

    public void sendWelcomeEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Benvenuto " + user.getNome());
        message.setText("Ciao " + user.getNome() + ", la tua registrazione è andata a buon fine!");
        message.setFrom("tuaemail@gmail.com");

        mailSender.send(message);
    }
}

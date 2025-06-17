package com.example.Based_security_project.Service;

import com.example.Based_security_project.Dto.UserDto;
import com.example.Based_security_project.Enumeration.Role;
import com.example.Based_security_project.Exception.NotFoundException;
import com.example.Based_security_project.Model.User;
import com.example.Based_security_project.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User saveUser(UserDto userDto){
        User user = new User();
        user.setNome(userDto.getNome());
        user.setCognome(userDto.getCognome());
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(Role.USER);

        return userRepository.save(user);
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public User getUser(int id) throws NotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User con id " + id + " non trovato"));
    }

    public User updateUser(int id, UserDto userDto) throws NotFoundException {
        User userDaAggiornare = getUser(id);

        userDaAggiornare.setNome(userDto.getNome());
        userDaAggiornare.setCognome(userDto.getCognome());
        userDaAggiornare.setUsername(userDto.getUsername());

        // Verifica se la password nuova è diversa da quella salvata
        if (!passwordEncoder.matches(userDto.getPassword(), userDaAggiornare.getPassword())) {
            userDaAggiornare.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        return userRepository.save(userDaAggiornare);
    }

    public void deleteUser(int id) throws NotFoundException {
        User userDaCancellare = getUser(id);
        userRepository.delete(userDaCancellare);
    }


}

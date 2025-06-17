package com.example.Based_security_project.Repository;

import com.example.Based_security_project.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    public Optional<User> findByUsername(String username);
}

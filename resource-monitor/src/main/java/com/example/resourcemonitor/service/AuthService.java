package com.example.resourcemonitor.service;

import com.example.resourcemonitor.model.User;
import com.example.resourcemonitor.repository.UserRepository;

public class AuthService {

    private final UserRepository userRepository;

    public AuthService() {
        this.userRepository = new UserRepository();
    }

    public boolean authenticate(String login, String password) {
        User user = userRepository.findByLoginAndPassword(login, password);
        return user != null;
    }
}
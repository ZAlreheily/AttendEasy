package com.example.AttendEasy.Auth;

import com.example.AttendEasy.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImp implements AuthService {
    UserRepository userRepository;

    @Autowired
    public AuthServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

}

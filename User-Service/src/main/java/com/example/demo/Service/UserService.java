package com.example.demo.Service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.Model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public interface UserService {
    public User registerUser(User user);
//    public ResponseEntity<?> authenticate(LoginRequest loginRequest);
    public User updateUser(User user);
    public User userDetails(String email);
}



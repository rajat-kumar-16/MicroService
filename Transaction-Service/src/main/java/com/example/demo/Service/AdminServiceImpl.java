package com.example.demo.Service;

import com.example.demo.Repository.AdminRepository;
import com.example.demo.dto.AdminLoginRequest;
import com.example.demo.dto.LoginRequest;
import com.example.demo.Model.Admin;
import com.example.demo.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService{
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Admin loginAdmin(String username, String password) {
        // Retrieve admin by username
        Admin admin = adminRepository.findByUsername(username);

        // Check if admin exists and password matches
        if (admin != null && admin.getPassword().equals(password)) {
            return admin;
        } else {
            return null; // Authentication failed
        }
    }
}

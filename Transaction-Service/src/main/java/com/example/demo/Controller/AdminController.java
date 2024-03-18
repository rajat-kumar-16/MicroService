package com.example.demo.Controller;

import com.example.demo.Repository.AdminRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.AdminService;
import com.example.demo.dto.AdminLoginRequest;
import com.example.demo.dto.LoginRequest;
import com.example.demo.Model.Admin;
import com.example.demo.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminRepository adminRepository, AdminService adminService, UserRepository userRepository) {
        this.adminRepository = adminRepository;
        this.adminService = adminService;
        this.userRepository = userRepository;;
    }


    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AdminLoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        Admin admin = adminService.loginAdmin(username, password);

        if (admin != null) {
            return ResponseEntity.ok("Admin authentication successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Admin authentication failed");
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/login")
    public ResponseEntity<String> loginAdmin() {
        // Logic to authenticate admin
        // For simplicity, let's just return a success message for now
        return ResponseEntity.ok("Admin login successful");
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        // Check if user with the given ID exists
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok("User deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}

package com.example.demo.Service;

import com.example.demo.Repository.AccountRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.AccountService;
import com.example.demo.dto.LoginRequest;
import com.example.demo.Exception.UserValidation;
import com.example.demo.Model.Account;
import com.example.demo.Model.User;
import com.example.demo.Util.LoggedinUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private WebClient webClient;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, AccountService accountService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.accountService = accountService;
        this.passwordEncoder =  passwordEncoder;
    }
    @Override
    public User registerUser(User user) {

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // Save the user details
        User savedUser = userRepository.save(user);

        // Create an account for the user
        Account account = accountService.createAccount(savedUser);

        savedUser.setAccount(account);
        userRepository.save(savedUser);

        System.out.println(savedUser.getAccount().getAccountNumber());
        System.out.println(account.getUser().getName());
        return savedUser;
    }

    @Override
    public User updateUser(User user) {

        User existingUser = userRepository.findByAccountAccountNumber(LoggedinUser.getAccountNumber());

        if(user.getEmail() != null){
            if(user.getEmail().isEmpty())
                throw new UserValidation("Email can't be empty");
            else
                existingUser.setEmail(user.getEmail());
        }
        if(user.getName() != null){
            if(user.getName().isEmpty())
                throw new UserValidation("Name can't be empty");
            else
                existingUser.setName(user.getName());
        }
        if(user.getPhone_number() != null){
            if(user.getPhone_number().isEmpty())
                throw new UserValidation("Phone number can't be empty");
            else
                existingUser.setPhone_number(user.getPhone_number());
        }
        if(user.getAddress() != null){
            existingUser.setAddress(user.getAddress());
        }
        return userRepository.save(existingUser);
    }
    @Override
    public User userDetails(String accountNumber) {
        return userRepository.findByAccountAccountNumber(accountNumber);
    }
}

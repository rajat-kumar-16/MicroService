package com.example.demo.Controller;

import com.example.demo.Service.AccountService;
import com.example.demo.Service.UserService;
import com.example.demo.Model.Account;
import com.example.demo.Model.User;
import com.example.demo.Util.LoggedinUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/home")
public class HomeController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;
    @GetMapping("/account")
    public Account AccountDetails(){
        return accountService.AccountDetails(LoggedinUser.getAccountNumber());
    }
    @GetMapping("/user")
    public User UserDetails(){
        return userService.userDetails(LoggedinUser.getAccountNumber());
    }
}

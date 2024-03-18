package com.example.demo.Exception;


public class UserValidation extends RuntimeException{

    public UserValidation(String message) {
        super(message);
    }
}
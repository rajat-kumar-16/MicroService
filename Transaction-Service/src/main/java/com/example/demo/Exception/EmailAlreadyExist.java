package com.example.demo.Exception;

public class EmailAlreadyExist extends RuntimeException{
    public EmailAlreadyExist(String message) {
        super(message);
    }
}

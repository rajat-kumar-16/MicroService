package com.example.demo.Service;

import com.example.demo.dto.TransactionDTO;
import com.example.demo.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionService {
    public List<TransactionDTO> getAllTransactionsByAccountNumber(String accountNumber);
}

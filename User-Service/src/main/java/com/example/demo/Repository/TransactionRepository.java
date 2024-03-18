package com.example.demo.Repository;

import com.example.demo.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySourceAccount_AccountNumberOrTargetAccount_AccountNumber(String accountNumber, String accountNumber1);
}

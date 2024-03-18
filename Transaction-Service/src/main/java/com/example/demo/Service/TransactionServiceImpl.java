package com.example.demo.Service;

import com.example.demo.Repository.TransactionRepository;
import com.example.demo.dto.TransactionDTO;
import com.example.demo.Model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Override
    public List<TransactionDTO> getAllTransactionsByAccountNumber(String accountNumber) {
        if(accountNumber==null) return new ArrayList<>();
        List<Transaction> transactions = transactionRepository.findBySourceAccount_AccountNumberOrTargetAccount_AccountNumber(accountNumber, accountNumber);

        List<TransactionDTO> transactionDTOs = transactions.stream().map(transaction -> convertToTransactionDTO(transaction)).sorted((t1, t2) -> t2.getTransaction_date().compareTo(t1.getTransaction_date())).collect((Collectors.toList()));
        return transactionDTOs;
    }
    public TransactionDTO convertToTransactionDTO(Transaction transaction){
        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId());
        dto.setAmount(transaction.getAmount());
        dto.setTransaction_type(transaction.getTransactionType());
        dto.setTransaction_date(transaction.getTransaction_date());
        dto.setSourceAccountNumber(transaction.getSourceAccount().getAccountNumber());
        if (transaction.getTargetAccount() != null) {
            dto.setTargetAccountNumber(transaction.getTargetAccount().getAccountNumber());
        } else {
            dto.setTargetAccountNumber("N/A");
        }
        return dto;
    }
}

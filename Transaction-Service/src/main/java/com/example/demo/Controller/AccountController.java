package com.example.demo.Controller;
import org.springframework.http.HttpHeaders;
import com.example.demo.Service.AccountService;
import com.example.demo.Service.TransactionService;
import com.example.demo.dto.*;
//import com.example.demo.Util.LoggedinUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;
@RestController
@RequestMapping("/api/account")
public class AccountController {
	@Autowired
	private WebClient.Builder webClientBuilder;
	@Autowired
	private WebClient webClient;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;
    @PostMapping("/pin/check")
    public ResponseEntity<?> checkAccountPIN(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
//    	System.out.println("1");
    	String token = authorizationHeader.replace("Bearer ", "");
    	String acno= webClientBuilder.build().get().uri("http://localhost:8082/User/api/users/auth",
    			uriBuilder -> uriBuilder.build())
    			.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(String.class)
                .block();
//    	System.out.println(ac);
        boolean isPINValid = accountService.isPinCreated(acno);
        Map<String, Object> result = new HashMap<>();
        result.put("hasPIN", isPINValid);

        if (isPINValid) {
            result.put("msg", "PIN Created");

        } else {
            result.put("msg", "Pin Not Created");
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PostMapping("/pin/create")
    public ResponseEntity<?> createPIN(@RequestBody PinRequest pinRequest, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
    	String token = authorizationHeader.replace("Bearer ", "");
    	String acno= webClientBuilder.build().get().uri("http://localhost:8082/User/api/users/auth",
    			uriBuilder -> uriBuilder.build())
    			.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        accountService.createPIN(acno, pinRequest.getPassword(), pinRequest.getPin());
        Map<String, String> response = new HashMap<>();
        response.put("msg", "PIN created successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);

    }
    @PostMapping("/pin/update")
    public ResponseEntity<?> updatePIN(@RequestBody PinUpdateRequest pinUpdateRequest,@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
    	String token = authorizationHeader.replace("Bearer ", "");
    	String acno= webClientBuilder.build().get().uri("http://localhost:8082/User/api/users/auth",
    			uriBuilder -> uriBuilder.build())
    			.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        accountService.updatePIN(acno, pinUpdateRequest.getOldPin(),
                pinUpdateRequest.getPassword(), pinUpdateRequest.getNewPin());

        Map<String, String> response = new HashMap<>();
        response.put("msg", "PIN updated successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);

    }
    @PostMapping("/deposit")
    public ResponseEntity<?> cashDeposit(@RequestBody AmountRequest amountRequest,@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {

        if (amountRequest.getAmount() <= 0) {
            Map<String, String> err = new HashMap<>();
            err.put("Error", "Invalid amount");
            return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
        }
        String token = authorizationHeader.replace("Bearer ", "");
    	String acno= webClientBuilder.build().get().uri("http://localhost:8082/User/api/users/auth",
    			uriBuilder -> uriBuilder.build())
    			.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        accountService.cashDeposit(acno, amountRequest.getPin(), amountRequest.getAmount());
        Map<String, String> response = new HashMap<>();
        response.put("msg", "Cash deposited successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> cashWithdrawal(@RequestBody AmountRequest amountRequest, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        if (amountRequest.getAmount() <= 0) {
            Map<String, String> err = new HashMap<>();
            err.put("Error", "Invalid amount");
            return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
        }
        String token = authorizationHeader.replace("Bearer ", "");
    	String acno= webClientBuilder.build().get().uri("http://localhost:8082/User/api/users/auth",
    			uriBuilder -> uriBuilder.build())
    			.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        accountService.cashWithdrawal(acno, amountRequest.getPin(), amountRequest.getAmount());
        Map<String, String> response = new HashMap<>();
        response.put("msg", "Cash withdrawn successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/fund-transfer")
    public ResponseEntity<?> fundTransfer(@RequestBody FundTransferRequest fundTransferRequest,@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        if (fundTransferRequest.getAmount() <= 0) {
            Map<String, String> err = new HashMap<>();
            err.put("Error", "Invalid amount");
            return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
        }
        String token = authorizationHeader.replace("Bearer ", "");
    	String acno= webClientBuilder.build().get().uri("http://localhost:8082/User/api/users/auth",
    			uriBuilder -> uriBuilder.build())
    			.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        accountService.fundTransfer(acno, fundTransferRequest.getTargetAccountNumber(),
                fundTransferRequest.getPin(), fundTransferRequest.getAmount());
        Map<String, String> response = new HashMap<>();
        response.put("msg", "Fund transferred successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionDTO>> getAllTransactionsByAccountNumber(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
    	String token = authorizationHeader.replace("Bearer ", "");
    	String acno= webClientBuilder.build().get().uri("http://localhost:8082/User/api/users/auth",
    			uriBuilder -> uriBuilder.build())
    			.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        List<TransactionDTO> transactions = transactionService
                .getAllTransactionsByAccountNumber(acno);
        return ResponseEntity.ok(transactions);
    }
}

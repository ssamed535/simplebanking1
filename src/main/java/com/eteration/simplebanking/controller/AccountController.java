package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.BillPaymentTransaction;
import com.eteration.simplebanking.model.DepositTransaction;
import com.eteration.simplebanking.model.WithdrawalTransaction;
import com.eteration.simplebanking.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account/v1")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/{accountNumber}")
    public ResponseEntity getAccount(@PathVariable String accountNumber) {
        return ResponseEntity.ok(accountService.findAccount(accountNumber));
    }
    @PostMapping("/credit/{accountNumber}")
    public ResponseEntity<TransactionStatus> credit(@PathVariable String accountNumber, @RequestBody DepositTransaction depositTransaction) throws Exception {
        Account account = accountService.findAccount(accountNumber);
        TransactionStatus transactionStatus = accountService.post(account, depositTransaction);
        return ResponseEntity.ok(transactionStatus);
    }
    @PostMapping("/debit/{accountNumber}")
    public ResponseEntity<TransactionStatus> debit(@PathVariable String accountNumber, @RequestBody WithdrawalTransaction withdrawalTransaction) throws Exception {
        Account account = accountService.findAccount(accountNumber);
        TransactionStatus transactionStatus = accountService.post(account, withdrawalTransaction);
        return ResponseEntity.ok(transactionStatus);
    }
    @PostMapping("/bill-payment/{accountNumber}")
    public ResponseEntity<TransactionStatus> billPayment(@PathVariable String accountNumber, @RequestBody BillPaymentTransaction billPaymentTransaction) throws Exception {
        Account account = accountService.findAccount(accountNumber);
        TransactionStatus transactionStatus = accountService.post(account, billPaymentTransaction);
        return ResponseEntity.ok(transactionStatus);
    }

    @PostMapping()
    public ResponseEntity save(@RequestBody Account account) {
        account = accountService.saveAccount(account);
        return ResponseEntity.ok(account);
    }
}
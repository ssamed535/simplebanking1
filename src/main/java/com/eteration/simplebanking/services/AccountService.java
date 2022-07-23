package com.eteration.simplebanking.services;

import com.eteration.simplebanking.controller.TransactionStatus;
import com.eteration.simplebanking.dao.AccountDao;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountDao accountDao;

    public Account findAccount(String accountNumber) {
        Account account = accountDao.findByAccountNumber(accountNumber);
        return account;
    }

    public TransactionStatus post(Account account, Transaction transaction) throws Exception {

        if (account == null)
            throw new Exception("User not found");
        String approvalCode = transaction.post(account);
        saveAccount(account);

        TransactionStatus transactionStatus = new TransactionStatus("OK", approvalCode);
        return transactionStatus;
    }

    public Account saveAccount(Account account) {
        account = accountDao.save(account);
        return account;
    }
}

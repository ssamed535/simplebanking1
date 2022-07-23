package com.eteration.simplebanking.model;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.Set;

@NoArgsConstructor
@Entity
public class DepositTransaction extends Transaction {

    public DepositTransaction(Double amount) {
        super(amount);
    }

    @Override
    public String getType() {
        return "DepositTransaction";
    }

    @Override
    public String post(Account account) {
        Double balance = account.getBalance();
        Double amount = getAmount();
        Set<Transaction> transactions = account.getTransactions();

        account.setBalance(balance + amount);

        this.setApprovalCode(generateApprovalCode());
        transactions.add(this);
        account.setTransactions(transactions);
        return this.getApprovalCode();
    }
}

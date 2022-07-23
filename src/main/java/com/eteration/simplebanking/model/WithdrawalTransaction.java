package com.eteration.simplebanking.model;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.Set;

@NoArgsConstructor
@Entity
public class WithdrawalTransaction extends Transaction {

    public WithdrawalTransaction(Double amount) {
        super(amount);
    }

    @Override
    public String getType() {
        return "WithdrawalTransaction";
    }

    @Override
    public String post(Account account) throws InsufficientBalanceException {
        Double balance = account.getBalance();
        Double amount = getAmount();
        Set<Transaction> transactions = account.getTransactions();

        if (balance < amount)
            throw new InsufficientBalanceException("Insufficient Balance");
        account.setBalance(balance - amount);

        this.setApprovalCode(generateApprovalCode());
        transactions.add(this);
        account.setTransactions(transactions);
        return this.getApprovalCode();
    }
}

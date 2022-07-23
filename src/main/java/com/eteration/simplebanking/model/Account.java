package com.eteration.simplebanking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ACCOUNT")
@Getter
@Setter
@NoArgsConstructor
public class Account {
    @Id
    @SequenceGenerator(name = "Account", sequenceName = "ACCOUNT_ID_SEQ")
    @GeneratedValue(generator = "Account")
    @JsonIgnore
    private Long id;
    private String owner;
    private String accountNumber;
    private Double balance = 0d;

    private Date createDate = new Date();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "account_id")
    private Set<Transaction> transactions = new HashSet<>();

    public Account(String owner, String accountNumber) {
        this.owner = owner;
        this.accountNumber = accountNumber;

    }

    public String post(Transaction transaction) throws InsufficientBalanceException {
        transaction.post(this);
        return transaction.getApprovalCode();

        /*if (transaction instanceof WithdrawalTransaction)
            withdraw(transaction.getAmount());
        if (transaction instanceof DepositTransaction)
            deposit(transaction.getAmount());*/
    }

    public Account withdraw(Double amount) throws InsufficientBalanceException {
        if (balance<amount)
            throw new InsufficientBalanceException("Insufficient Balance");
        balance-=amount;
        transactions.add(new WithdrawalTransaction(amount));
        return this;
    }

    public Account deposit(Double amount) {
        balance += amount;
        transactions.add(new DepositTransaction(amount));
        return this;
    }
}

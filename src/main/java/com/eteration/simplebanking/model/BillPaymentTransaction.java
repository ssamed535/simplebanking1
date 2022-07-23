package com.eteration.simplebanking.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class BillPaymentTransaction extends WithdrawalTransaction {
    private String payee;

    public BillPaymentTransaction(String payee, Double amount) {
        super(amount);
        this.payee =payee;
    }

    @Override
    public String getType() {
        return "BillPaymentTransaction";
    }
}

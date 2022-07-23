package com.eteration.simplebanking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TRANSACTION")
@Getter
@Setter
@NoArgsConstructor
public abstract class Transaction {
    @Id
    @SequenceGenerator(name = "Transaction", sequenceName = "TRANSACTION_ID_SEQ")
    @GeneratedValue(generator = "Transaction")
    @JsonIgnore
    private Long id;
    private Date date = new Date();
    private Double amount;
    private String approvalCode;

    @Override
    public String toString() {
        return "Date=" + date + ",\n Amount=" + amount;
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
    public abstract String getType();

    public abstract String post(Account account) throws InsufficientBalanceException;

    public Transaction(Double amount) {
        this.amount = amount;
    }

    public String generateApprovalCode() {
        Integer[] chunkLengths = {8, 4, 4, 12};
        String approvalCode = "";
        for (Integer length: chunkLengths) {
            approvalCode += RandomStringUtils.randomAlphanumeric(length) + "-";
        }
        if (approvalCode != null || approvalCode != "")
            approvalCode = StringUtils.substring(approvalCode, 0, approvalCode.length() - 1);
        this.approvalCode = approvalCode;
        return approvalCode;
    }
}

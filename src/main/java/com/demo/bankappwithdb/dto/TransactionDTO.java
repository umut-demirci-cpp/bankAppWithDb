package com.demo.bankappwithdb.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionDTO {

    private String type;
    private double amount;
    private LocalDateTime timestamp;
    private double balanceAfterTransaction;
}
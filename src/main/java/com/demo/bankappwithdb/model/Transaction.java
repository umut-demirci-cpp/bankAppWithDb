package com.demo.bankappwithdb.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")  // transaction tablosundaki foreign key sütunu
    private Customer customer;

    private String type;

    private double amount;

    private LocalDateTime timestamp;

    private double balanceAfterTransaction;
}
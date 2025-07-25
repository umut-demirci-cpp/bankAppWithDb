package com.demo.bankappwithdb.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private String transactionType; // DEPOSIT, WITHDRAW, TRANSFER_SENT, TRANSFER_RECEIVED
    private double amount;
    private String description;
    private LocalDateTime timestamp;
    private Double balanceAfterTransaction;

    // Getter ve Setter metodları
    // Default constructor
    public Transaction() {
        this.timestamp = LocalDateTime.now();
    }

    // Parametreli constructor
    public Transaction(Customer customer, String transactionType, double amount, String description, double balanceAfterTransaction) {
        this.customer = customer;
        this.transactionType = transactionType;
        this.amount = amount;
        this.description = description;
        this.balanceAfterTransaction = balanceAfterTransaction;
        this.timestamp = LocalDateTime.now();
    }

    // Getter ve Setter metodları buraya eklenecek

    public Long getId() {
        return id;
    }
    public Customer getCustomer() {
        return customer;
    }
    public String getTransactionType() {
        return transactionType;
    }
    public double getAmount() {
        return amount;
    }
    public String getDescription() {
        return description;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public double getBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    public void setBalanceAfterTransaction(double balanceAfterTransaction) {
        this.balanceAfterTransaction = balanceAfterTransaction;
    }
}

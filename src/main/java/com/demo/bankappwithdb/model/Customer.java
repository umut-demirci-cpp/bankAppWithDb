package com.demo.bankappwithdb.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "customer_table")
public class Customer {

    @Id
    private Long id;

    @Column(name = "username")
    private String name;

    private String surname;

    private String password;

    private String iban;

    private double balance;

    private int age;
}
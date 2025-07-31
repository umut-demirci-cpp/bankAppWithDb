package com.demo.bankappwithdb.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDTO {

    private Long id;

    private String name;

    private String surname;

    private String password;

    private String iban;

    private double balance;

    private int age;
}
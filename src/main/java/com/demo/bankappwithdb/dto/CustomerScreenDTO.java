package com.demo.bankappwithdb.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerScreenDTO {

    private String name;

    private String surname;

    private String iban;

    private double balance;

}
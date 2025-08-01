package com.demo.bankappwithdb.utility;

import java.util.Random;

public class IbanGeneratorUtil {

    public static String generateIban() {
        Random random = new Random();
        StringBuilder iban = new StringBuilder("TR");
        for (int i = 0; i < 20; i++) {
            iban.append(random.nextInt(10));
        }
        return iban.toString();
    }
}
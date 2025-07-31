package com.demo.bankappwithdb.mapper;

import com.demo.bankappwithdb.dto.TransactionDTO;
import com.demo.bankappwithdb.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public TransactionDTO toDto(Transaction tx) {
        TransactionDTO dto = new TransactionDTO();
        dto.setType(tx.getType());
        dto.setAmount(tx.getAmount());
        dto.setTimestamp(tx.getTimestamp());
        dto.setBalanceAfterTransaction(tx.getBalanceAfterTransaction());
        return dto;
    }
}
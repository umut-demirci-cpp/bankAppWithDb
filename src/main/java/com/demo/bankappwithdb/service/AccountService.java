package com.demo.bankappwithdb.service;

import com.demo.bankappwithdb.model.Customer;
import com.demo.bankappwithdb.model.Transaction;
import com.demo.bankappwithdb.repository.CustomerRepository;
import com.demo.bankappwithdb.repository.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AccountService {

    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(CustomerRepository customerRepository, TransactionRepository transactionRepository) {
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
    }

    public void deposit(Customer customer, double amount) {
        customer.setBalance(customer.getBalance() + amount);
        customerRepository.save(customer);

        Transaction transaction = new Transaction();
        transaction.setCustomer(customer);
        transaction.setAmount(amount);
        transaction.setType("DEPOSIT");
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setBalanceAfterTransaction(customer.getBalance());
        transactionRepository.save(transaction);
    }

    public void withdraw(Customer customer, double amount) {
        if (customer.getBalance() >= amount) {
            customer.setBalance(customer.getBalance() - amount);
            customerRepository.save(customer);

            Transaction transaction = new Transaction();
            transaction.setCustomer(customer);
            transaction.setAmount(amount);
            transaction.setType("WITHDRAW");
            transaction.setTimestamp(LocalDateTime.now());
            transaction.setBalanceAfterTransaction(customer.getBalance());
            transactionRepository.save(transaction);
        }
    }

    public void transfer(Customer sender, Customer receiver, double amount) {
        if (sender.getBalance() >= amount) {
            sender.setBalance(sender.getBalance() - amount);
            receiver.setBalance(receiver.getBalance() + amount);

            customerRepository.save(sender);
            customerRepository.save(receiver);

            Transaction senderTx = new Transaction();
            senderTx.setCustomer(sender);
            senderTx.setAmount(amount);
            senderTx.setType("TRANSFER_SENT");
            senderTx.setTimestamp(LocalDateTime.now());
            senderTx.setBalanceAfterTransaction(sender.getBalance());
            transactionRepository.save(senderTx);

            Transaction receiverTx = new Transaction();
            receiverTx.setCustomer(receiver);
            receiverTx.setAmount(amount);
            receiverTx.setType("TRANSFER_RECEIVED");
            receiverTx.setTimestamp(LocalDateTime.now());
            receiverTx.setBalanceAfterTransaction(receiver.getBalance());
            transactionRepository.save(receiverTx);
        }
    }

    public Page<Transaction> getTransactionsByCustomerId(Long customerId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return transactionRepository.findByCustomerIdOrderByTimestampDesc(customerId, pageable);
    }
}
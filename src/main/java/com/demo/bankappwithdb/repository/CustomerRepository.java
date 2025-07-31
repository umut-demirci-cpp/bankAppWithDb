package com.demo.bankappwithdb.repository;

import com.demo.bankappwithdb.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByIban(String iban);
    boolean existsByIban(String iban);
}
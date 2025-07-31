package com.demo.bankappwithdb.repository;

import com.demo.bankappwithdb.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Page<Transaction> findByCustomerIdOrderByTimestampDesc(Long customerId, Pageable pageable);

    // List<Transaction> findByCustomerId(Long customerId);
}

package com.demo.bankappwithdb.service;

import com.demo.bankappwithdb.dto.CustomerDTO;
import com.demo.bankappwithdb.mapper.CustomerMapper;
import com.demo.bankappwithdb.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final CustomerRepository customerRepository;

    public LoginService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerDTO login(long id, String password) {
        return customerRepository.findById(id)
                .filter(c -> c.getPassword().equals(password))
                .map(CustomerMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Giriş başarısız."));
    }
}
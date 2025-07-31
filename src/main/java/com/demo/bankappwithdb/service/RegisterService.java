package com.demo.bankappwithdb.service;

import com.demo.bankappwithdb.dto.CustomerDTO;
import com.demo.bankappwithdb.mapper.CustomerMapper;
import com.demo.bankappwithdb.model.Customer;
import com.demo.bankappwithdb.repository.CustomerRepository;
import com.demo.bankappwithdb.utility.IbanGenerator;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    private final CustomerRepository customerRepository;

    public RegisterService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void register(CustomerDTO customerDTO) {
        String iban = IbanGenerator.generateIban();

        while (customerRepository.existsByIban(iban)) {
            iban = IbanGenerator.generateIban();
        }

        customerDTO.setIban(iban);
        Customer customer = CustomerMapper.toEntity(customerDTO);

        customerRepository.save(customer);
    }
}
package com.demo.bankappwithdb.mapper;

import com.demo.bankappwithdb.dto.CustomerDTO;
import com.demo.bankappwithdb.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public static CustomerDTO toDto(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setSurname(customer.getSurname());
        dto.setPassword(customer.getPassword());
        dto.setIban(customer.getIban());
        dto.setBalance(customer.getBalance());
        dto.setAge(customer.getAge());
        return dto;
    }

    public static Customer toEntity(CustomerDTO customerDTO) {

        Customer customer = new Customer();
        customer.setId(customerDTO.getId());
        customer.setName(customerDTO.getName());
        customer.setSurname(customerDTO.getSurname());
        customer.setPassword(customerDTO.getPassword());
        customer.setIban(customerDTO.getIban());
        customer.setBalance(customerDTO.getBalance());
        customer.setAge(customerDTO.getAge());
        return customer;
    }
}
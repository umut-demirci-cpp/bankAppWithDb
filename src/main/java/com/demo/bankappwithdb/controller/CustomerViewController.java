package com.demo.bankappwithdb.controller;

import com.demo.bankappwithdb.model.Customer;
import com.demo.bankappwithdb.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
class RegisterController {

    @Autowired
    private CustomerRepository repository;

    @PostMapping("/register")
    public String register(@RequestParam String name,
                           @RequestParam String email,
                           @RequestParam String password,
                           @RequestParam double balance,
                           Model model) {

        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPassword(password);
        customer.setBalance(balance);

        repository.save(customer);

        model.addAttribute("customer", customer); // account.html sayfası için
        return "account"; // templates/account.html dosyasını döndürür
    }
}
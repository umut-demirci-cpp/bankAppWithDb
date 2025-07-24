package com.demo.bankappwithdb.controller;

import com.demo.bankappwithdb.model.Customer;
import com.demo.bankappwithdb.repository.CustomerRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // login.html
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        Customer customer = customerRepository.findByEmailAndPassword(email, password);

        if (customer != null) {
            session.setAttribute("loggedInCustomer", customer);
            model.addAttribute("customer", customer);
            return "account"; // account.html
        } else {
            model.addAttribute("error", "Geçersiz e-posta veya şifre!");
            return "login"; // tekrar login sayfası
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
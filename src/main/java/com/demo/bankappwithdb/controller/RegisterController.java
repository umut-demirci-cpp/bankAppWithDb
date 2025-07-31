package com.demo.bankappwithdb.controller;

import com.demo.bankappwithdb.dto.CustomerDTO;
import com.demo.bankappwithdb.service.RegisterService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @GetMapping
    public String showRegisterForm(org.springframework.ui.Model model) {
        model.addAttribute("customer", new CustomerDTO());
        return "register";
    }


    @PostMapping
    public String register(@ModelAttribute CustomerDTO customerDTO) {
        registerService.register(customerDTO);
        return "redirect:/login";
    }

}
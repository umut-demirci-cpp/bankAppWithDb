package com.demo.bankappwithdb.controller;

import com.demo.bankappwithdb.dto.CustomerDTO;
import com.demo.bankappwithdb.service.LoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping
    public String loginForm() {
        return "login";
    }

    @PostMapping
    public String login(@RequestParam long id,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        try {
            CustomerDTO customerDTO = loginService.login(id, password);
            session.setAttribute("customer", customerDTO);
            return "redirect:/account";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "login";
        }
    }
}
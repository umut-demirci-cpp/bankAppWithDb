package com.demo.bankappwithdb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home"; // home.html sayfasını döndürür
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    /*
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
    */

}

package com.demo.bankappwithdb.controller;

import com.demo.bankappwithdb.dto.CustomerDTO;
import com.demo.bankappwithdb.dto.TransactionDTO;
import com.demo.bankappwithdb.mapper.CustomerMapper;
import com.demo.bankappwithdb.mapper.TransactionMapper;
import com.demo.bankappwithdb.model.Customer;
import com.demo.bankappwithdb.model.Transaction;
import com.demo.bankappwithdb.repository.CustomerRepository;
import com.demo.bankappwithdb.service.AccountService;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;
    private final CustomerRepository customerRepository;
    private final TransactionMapper transactionMapper;

    public AccountController(AccountService accountService,
                             CustomerRepository customerRepository,
                             TransactionMapper transactionMapper) {
        this.accountService = accountService;
        this.customerRepository = customerRepository;
        this.transactionMapper = transactionMapper;
    }

    @GetMapping
    public String accountPage(HttpSession session, Model model) {
        CustomerDTO customerDTO = (CustomerDTO) session.getAttribute("customer");
        if (customerDTO == null) {
            // Müşteri yoksa login sayfasına yönlendir
            return "redirect:/login";
        }
        model.addAttribute("customer", customerDTO);
        return "account";
    }

    // İşlem geçmişi sayfası için GET metodu
    @GetMapping("/transactions")
    public String transactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            HttpSession session,
            Model model) {

        CustomerDTO customerDTO = (CustomerDTO) session.getAttribute("customer");
        if (customerDTO == null) {
            return "redirect:/login";
        }

        Long customerId = customerDTO.getId();
        Page<Transaction> transactionPage = accountService.getTransactionsByCustomerId(customerId, page, size);

        List<TransactionDTO> transactions = transactionPage.getContent()
                .stream()
                .map(transactionMapper::toDto)
                .collect(Collectors.toList());

        model.addAttribute("transactions", transactions);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", transactionPage.getTotalPages());
        model.addAttribute("pageSize", size);
        model.addAttribute("customer", customerDTO);

        return "transactions";  // transactions.html sayfası
    }

    @PostMapping("/deposit")
    public String deposit(@RequestParam double amount, HttpSession session) {
        CustomerDTO customerDTO = (CustomerDTO) session.getAttribute("customer");
        Customer customer = CustomerMapper.toEntity(customerDTO);
        accountService.deposit(customer, amount);

        session.setAttribute("customer", CustomerMapper.toDto(customer));
        return "redirect:/account";
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestParam double amount, HttpSession session) {
        CustomerDTO customerDTO = (CustomerDTO) session.getAttribute("customer");
        Customer customer = CustomerMapper.toEntity(customerDTO);
        accountService.withdraw(customer, amount);

        session.setAttribute("customer", CustomerMapper.toDto(customer));
        return "redirect:/account";
    }

    @PostMapping("/transfer")
    public String transfer(@RequestParam String toIban,
                           @RequestParam double amount,
                           HttpSession session) {
        CustomerDTO senderDTO = (CustomerDTO) session.getAttribute("customer");
        Customer sender = CustomerMapper.toEntity(senderDTO);

        customerRepository.findByIban(toIban)
                .ifPresent(receiver -> {
                    accountService.transfer(sender, receiver, amount);
                    session.setAttribute("customer", CustomerMapper.toDto(sender));
                });

        return "redirect:/account";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/transactions/load")
    @ResponseBody
    public List<TransactionDTO> loadTransactions(HttpSession session,
                                                 @RequestParam int page,
                                                 @RequestParam(defaultValue = "5") int size) {
        CustomerDTO customerDTO = (CustomerDTO) session.getAttribute("customer");
        if (customerDTO == null) {
            return List.of(); // Boş liste
        }

        Long customerId = customerDTO.getId();
        Page<Transaction> transactionPage = accountService.getTransactionsByCustomerId(customerId, page, size);

        return transactionPage.getContent()
                .stream()
                .map(transactionMapper::toDto)
                .collect(Collectors.toList());
    }
}
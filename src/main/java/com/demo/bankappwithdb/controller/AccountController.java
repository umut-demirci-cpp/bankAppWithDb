package com.demo.bankappwithdb.controller;

import com.demo.bankappwithdb.model.Customer;
import com.demo.bankappwithdb.model.Transaction;
import com.demo.bankappwithdb.repository.CustomerRepository;
import com.demo.bankappwithdb.repository.TransactionRepository;
import com.demo.bankappwithdb.utility.ValidationUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping
    public String showAccountPage(HttpSession session, Model model) {
        Customer loggedInCustomer = (Customer) session.getAttribute("loggedInCustomer");
        if (loggedInCustomer == null) {
            return "redirect:/login";
        }

        Customer freshCustomer = customerRepository.findById(loggedInCustomer.getId()).orElse(null);
        assert freshCustomer != null;
        List<Transaction> transactions = transactionRepository.findByCustomerIdOrderByTimestampDesc(freshCustomer.getId());

        model.addAttribute("customer", freshCustomer);
        model.addAttribute("transactions", transactions);

        return "account";
    }

    @PostMapping("/deposit")
    public String deposit(@RequestParam double amount, HttpSession session, RedirectAttributes redirectAttributes) {
        if (ValidationUtil.rejectIfInvalidAmount(amount, redirectAttributes)) {
            return "redirect:/account";
        }

        Customer loggedInCustomer = (Customer) session.getAttribute("loggedInCustomer");
        if (loggedInCustomer == null) {
            return "redirect:/login";
        }

        Customer customer = customerRepository.findById(loggedInCustomer.getId()).orElse(null);
        if (customer != null) {
            customer.setBalance(customer.getBalance() + amount);
            customerRepository.save(customer);

            Transaction transaction = new Transaction(customer, "DEPOSIT", amount,
                    "Para yatırma işlemi", customer.getBalance());
            transactionRepository.save(transaction);

            session.setAttribute("loggedInCustomer", customer);
            redirectAttributes.addFlashAttribute("success", amount + " TL yatırıldı.");
        }
        return "redirect:/account";
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestParam double amount, HttpSession session, RedirectAttributes redirectAttributes) {
        if (ValidationUtil.rejectIfInvalidAmount(amount, redirectAttributes)) {
            return "redirect:/account";
        }

        Customer loggedInCustomer = (Customer) session.getAttribute("loggedInCustomer");
        if (loggedInCustomer == null) {
            return "redirect:/login";
        }

        Customer customer = customerRepository.findById(loggedInCustomer.getId()).orElse(null);
        if (customer != null) {
            if (customer.getBalance() < amount) {
                redirectAttributes.addFlashAttribute("error", "Yeterli bakiye yok.");
                return "redirect:/account";
            }

            customer.setBalance(customer.getBalance() - amount);
            customerRepository.save(customer);

            Transaction transaction = new Transaction(customer, "WITHDRAW", amount,
                    "Para çekme işlemi", customer.getBalance());
            transactionRepository.save(transaction);

            session.setAttribute("loggedInCustomer", customer);
            redirectAttributes.addFlashAttribute("success", amount + " TL çekildi.");
        }
        return "redirect:/account";
    }

    @PostMapping("/transfer")
    public String transfer(@RequestParam String recipientEmail,
                           @RequestParam double amount,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {

        if (ValidationUtil.rejectIfInvalidAmount(amount, redirectAttributes)) {
            return "redirect:/account";
        }

        Customer sender = (Customer) session.getAttribute("loggedInCustomer");
        if (sender == null) {
            return "redirect:/login";
        }

        if (sender.getEmail().equals(recipientEmail)) {
            redirectAttributes.addFlashAttribute("error", "Kendinize transfer yapamazsınız.");
            return "redirect:/account";
        }

        Customer senderFresh = customerRepository.findById(sender.getId()).orElse(null);
        Customer recipient = customerRepository.findByEmail(recipientEmail);

        if (senderFresh == null || recipient == null) {
            redirectAttributes.addFlashAttribute("error", "Gönderici veya alıcı bulunamadı.");
            return "redirect:/account";
        }

        if (senderFresh.getBalance() < amount) {
            redirectAttributes.addFlashAttribute("error", "Yeterli bakiye yok.");
            return "redirect:/account";
        }

        senderFresh.setBalance(senderFresh.getBalance() - amount);
        recipient.setBalance(recipient.getBalance() + amount);

        customerRepository.save(senderFresh);
        customerRepository.save(recipient);

        Transaction senderTransaction = new Transaction(senderFresh, "TRANSFER_SENT", amount,
                "Transfer edilen kişi: " + recipientEmail, senderFresh.getBalance());
        Transaction recipientTransaction = new Transaction(recipient, "TRANSFER_RECEIVED", amount,
                "Gönderen kişi: " + sender.getEmail(), recipient.getBalance());

        transactionRepository.save(senderTransaction);
        transactionRepository.save(recipientTransaction);

        session.setAttribute("loggedInCustomer", senderFresh);
        redirectAttributes.addFlashAttribute("success", amount + " TL başarıyla transfer edildi.");
        return "redirect:/account";
    }
}

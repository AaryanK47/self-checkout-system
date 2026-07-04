package com.selfcheckout.controller;

import com.selfcheckout.entity.Transaction;
import com.selfcheckout.repository.TransactionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ReceiptController {

    private final TransactionRepository transactionRepository;

    public ReceiptController(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @GetMapping("/receipt/{id}")
    public String showReceipt(
            @PathVariable Long id,
            Model model) {

        Transaction transaction = transactionRepository
                .findWithItemsById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        model.addAttribute("transaction", transaction);

        return "receipt";
    }
}
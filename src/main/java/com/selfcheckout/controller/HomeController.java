package com.selfcheckout.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "checkout";
    }

    @GetMapping("/checkout")
    public String checkout() {
        return "checkout";
    }
}
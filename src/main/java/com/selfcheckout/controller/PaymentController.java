package com.selfcheckout.controller;

import com.selfcheckout.model.Cart;
import com.selfcheckout.service.CartService;
import com.selfcheckout.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;
    private final CartService cartService;

    public PaymentController(PaymentService paymentService,
                             CartService cartService) {
        this.paymentService = paymentService;
        this.cartService = cartService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<Long> checkout(
            @RequestParam String paymentMethod) {

        Cart cart = cartService.getCart();

        Long transactionId = paymentService.checkout(cart, paymentMethod);

        return ResponseEntity.ok(transactionId);
    }
}
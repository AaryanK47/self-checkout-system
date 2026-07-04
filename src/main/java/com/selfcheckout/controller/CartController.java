package com.selfcheckout.controller;

import com.selfcheckout.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add/{productId}")
    public ResponseEntity<?> addToCart(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "1") int qty) {

        cartService.addToCart(productId, qty);

        return ResponseEntity.ok("Product added to cart successfully.");
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<?> removeFromCart(@PathVariable Long productId) {

        cartService.removeFromCart(productId);

        return ResponseEntity.ok("Product removed successfully.");
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<?> updateQuantity(
            @PathVariable Long productId,
            @RequestParam int qty) {

        cartService.updateQuantity(productId, qty);

        return ResponseEntity.ok("Quantity updated successfully.");
    }

    @GetMapping
    public ResponseEntity<?> viewCart() {

        Map<String, Object> response = new LinkedHashMap<>();

        response.put("items", cartService.getCart().getItems());
        response.put("subtotal", cartService.getSubtotal());
        response.put("tax", cartService.getTax());
        response.put("total", cartService.getTotal());

        return ResponseEntity.ok(response);
    }
}
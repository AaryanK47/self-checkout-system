package com.selfcheckout.service;

import com.selfcheckout.entity.Product;
import com.selfcheckout.model.Cart;
import com.selfcheckout.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.math.BigDecimal;

@Service
@SessionScope
public class CartService {

    private static final BigDecimal TAX_RATE = new BigDecimal("0.05");

    private final Cart cart = new Cart();
    private final ProductRepository productRepository;

    public CartService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addToCart(Long productId, int quantity) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        cart.addItem(product, quantity);
    }

    public void removeFromCart(Long productId) {
        cart.removeItem(productId);
    }

    public void updateQuantity(Long productId, int quantity) {
        cart.updateQuantity(productId, quantity);
    }

    public Cart getCart() {
        return cart;
    }

    public BigDecimal getSubtotal() {
        return cart.getSubtotal();
    }

    public BigDecimal getTax() {
        return cart.getTax(TAX_RATE);
    }

    public BigDecimal getTotal() {
        return cart.getTotal(TAX_RATE);
    }
}
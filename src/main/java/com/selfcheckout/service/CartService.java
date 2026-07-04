package com.selfcheckout.service;

import com.selfcheckout.entity.Product;
import com.selfcheckout.model.Cart;
import com.selfcheckout.model.CartItem;
import com.selfcheckout.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.math.BigDecimal;
import static com.selfcheckout.config.AppConstants.TAX_RATE;

@Service
@SessionScope
public class CartService {

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

    public void clearCart() {
        cart.clearCart();
    }

    public void increaseQuantity(Long productId) {

        CartItem item = cart.getItems()
                .stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));

        cart.updateQuantity(productId, item.getQuantity() + 1);
    }

    public void decreaseQuantity(Long productId) {

        CartItem item = cart.getItems()
                .stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));

        cart.updateQuantity(productId, item.getQuantity() - 1);
    }
}
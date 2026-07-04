package com.selfcheckout.model;

import com.selfcheckout.entity.Product;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public class Cart {

    private final Map<Long, CartItem> items = new LinkedHashMap<>();

    public void addItem(Product product, int quantity) {

        items.merge(
                product.getId(),
                new CartItem(product, quantity),
                (existingItem, newItem) -> {
                    existingItem.setQuantity(existingItem.getQuantity() + quantity);
                    return existingItem;
                }
        );
    }

    public void removeItem(Long productId) {
        items.remove(productId);
    }

    public void updateQuantity(Long productId, int quantity) {

        if (quantity <= 0) {
            removeItem(productId);
            return;
        }

        CartItem cartItem = items.get(productId);

        if (cartItem != null) {
            cartItem.setQuantity(quantity);
        }
    }

    public BigDecimal getSubtotal() {

        return items.values()
                .stream()
                .map(CartItem::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTax(BigDecimal taxRate) {

        return getSubtotal().multiply(taxRate);
    }

    public BigDecimal getTotal(BigDecimal taxRate) {

        return getSubtotal().add(getTax(taxRate));
    }

    public Collection<CartItem> getItems() {
        return items.values();
    }

    public void clearCart() {
        items.clear();
    }

}
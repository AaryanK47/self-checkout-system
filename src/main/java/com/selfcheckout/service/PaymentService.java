package com.selfcheckout.service;

import com.selfcheckout.entity.Product;
import com.selfcheckout.entity.Transaction;
import com.selfcheckout.entity.TransactionItem;
import com.selfcheckout.model.Cart;
import com.selfcheckout.model.CartItem;
import com.selfcheckout.repository.ProductRepository;
import com.selfcheckout.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.selfcheckout.config.AppConstants.TAX_RATE;

@Service
public class PaymentService {

    private final TransactionRepository transactionRepository;
    private final ProductRepository productRepository;

    public PaymentService(TransactionRepository transactionRepository,
                          ProductRepository productRepository) {
        this.transactionRepository = transactionRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Long checkout(Cart cart, String paymentMethod) {

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty.");
        }

        Transaction transaction = new Transaction();

        transaction.setTransactionTime(LocalDateTime.now());
        transaction.setSubtotal(cart.getSubtotal());
        transaction.setTax(cart.getTax(TAX_RATE));
        transaction.setTotal(cart.getTotal(TAX_RATE));
        transaction.setPaymentMethod(paymentMethod);

        for (CartItem cartItem : cart.getItems()) {

            Product product = cartItem.getProduct();

            if (product.getStockQty() < cartItem.getQuantity()) {
                throw new RuntimeException(product.getName() + " is out of stock.");
            }

            product.setStockQty(product.getStockQty() - cartItem.getQuantity());
            productRepository.save(product);

            TransactionItem transactionItem = new TransactionItem();
            transactionItem.setTransaction(transaction);
            transactionItem.setProduct(product);
            transactionItem.setQuantity(cartItem.getQuantity());
            transactionItem.setPrice(product.getPrice());

            transaction.getItems().add(transactionItem);
        }

        Transaction savedTransaction = transactionRepository.save(transaction);

        cart.clearCart();

        return savedTransaction.getId();
    }
}
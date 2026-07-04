package com.selfcheckout.config;

import com.selfcheckout.entity.Product;
import com.selfcheckout.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataSeeder implements CommandLineRunner {

    private final ProductRepository productRepository;

    public DataSeeder(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) {

        if (productRepository.count() == 0) {

            productRepository.save(new Product(
                    null,
                    "Milk 1L",
                    "1001",
                    new BigDecimal("55.00"),
                    50,
                    "Dairy"
            ));

            productRepository.save(new Product(
                    null,
                    "Bread Loaf",
                    "1002",
                    new BigDecimal("40.00"),
                    30,
                    "Bakery"
            ));

            productRepository.save(new Product(
                    null,
                    "Eggs (12 Pack)",
                    "1003",
                    new BigDecimal("90.00"),
                    40,
                    "Dairy"
            ));

            productRepository.save(new Product(
                    null,
                    "Rice 5kg",
                    "1004",
                    new BigDecimal("350.00"),
                    20,
                    "Grocery"
            ));

            productRepository.save(new Product(
                    null,
                    "Toothpaste",
                    "1005",
                    new BigDecimal("60.00"),
                    25,
                    "Personal Care"
            ));

            System.out.println("Sample products inserted successfully.");
        }
    }
}
package com.selfcheckout.service;

import com.selfcheckout.entity.Product;
import com.selfcheckout.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<Product> getProductByBarcode(String barcode) {
        return productRepository.findByBarcode(barcode);
    }
    private static final Map<String, List<String>> FREQUENTLY_BOUGHT_TOGETHER = Map.ofEntries(

            Map.entry("Milk", List.of("Bread", "Butter", "Eggs (12 pcs)")),
            Map.entry("Bread", List.of("Butter", "Jam")),
            Map.entry("Butter", List.of("Bread", "Jam")),
            Map.entry("Eggs (12 pcs)", List.of("Bread", "Milk")),

            Map.entry("Tea Powder", List.of("Biscuits", "Milk", "Sugar (1 kg)")),
            Map.entry("Coffee Powder", List.of("Milk", "Sugar (1 kg)")),
            Map.entry("Biscuits", List.of("Tea Powder", "Milk")),

            Map.entry("Pasta", List.of("Tomato Ketchup", "Cheese", "Soft Drink")),
            Map.entry("Instant Noodles", List.of("Soft Drink", "Tomato Ketchup")),

            Map.entry("Shampoo", List.of("Soap", "Hand Wash")),
            Map.entry("Toothpaste", List.of("Toothbrush")),

            Map.entry("Rice (1 kg)", List.of("Toor Dal", "Cooking Oil (1 L)")),
            Map.entry("Wheat Flour (5 kg)", List.of("Cooking Oil (1 L)", "Ghee (500 ml)"))
    );

    public List<Product> getFrequentlyBoughtTogether(String barcode) {

        Optional<Product> scannedProduct = productRepository.findByBarcode(barcode);

        if (scannedProduct.isEmpty()) {
            return List.of();
        }

        List<String> relatedNames =
                FREQUENTLY_BOUGHT_TOGETHER.getOrDefault(scannedProduct.get().getName(), List.of());

        if (relatedNames.isEmpty()) {
            return List.of();
        }

        return productRepository.findByNameIn(relatedNames);
    }
}
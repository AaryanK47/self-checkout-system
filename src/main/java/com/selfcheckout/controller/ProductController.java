package com.selfcheckout.controller;

import com.selfcheckout.entity.Product;
import com.selfcheckout.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/recommendations/{barcode}")
    public ResponseEntity<?> getRecommendations(@PathVariable String barcode) {

        List<Product> recommendations = productService.getFrequentlyBoughtTogether(barcode);

        return ResponseEntity.ok(recommendations);
    }
}
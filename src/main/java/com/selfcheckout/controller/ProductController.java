package com.selfcheckout.controller;

import com.selfcheckout.entity.Product;
import com.selfcheckout.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // ==========================================
    // Frequently Bought Together
    // ==========================================

    @GetMapping("/recommendations/{barcode}")
    public ResponseEntity<?> getRecommendations(@PathVariable String barcode) {

        List<Product> recommendations =
                productService.getFrequentlyBoughtTogether(barcode);

        return ResponseEntity.ok(recommendations);

    }

    // ==========================================
    // Smart Recommendations
    // ==========================================

    @GetMapping("/recommended")
    public ResponseEntity<?> getRecommendedProducts(
            @RequestParam(required = false) String lastScannedBarcode) {

        Map<String, List<Product>> recommendations =
                productService.getRecommendedProducts(lastScannedBarcode);

        return ResponseEntity.ok(recommendations);

    }

    @GetMapping("/search")
    public ResponseEntity<?> searchProduct(
            @RequestParam String name) {

        return productService
                .searchByName(name)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

}
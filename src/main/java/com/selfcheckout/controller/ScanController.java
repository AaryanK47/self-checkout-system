package com.selfcheckout.controller;

import com.selfcheckout.entity.Product;
import com.selfcheckout.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/scan")
public class ScanController {

    private final ProductService productService;

    public ScanController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{barcode}")
    public ResponseEntity<?> scanProduct(@PathVariable String barcode) {

        Optional<Product> product = productService.getProductByBarcode(barcode);

        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        }

        return ResponseEntity.status(404)
                .body("Product not found for barcode: " + barcode);
    }
}
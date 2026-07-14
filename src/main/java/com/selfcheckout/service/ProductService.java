package com.selfcheckout.service;

import com.selfcheckout.entity.Product;
import com.selfcheckout.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.selfcheckout.model.CartItem;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final CartService cartService;

    public ProductService(ProductRepository productRepository, CartService cartService) {
        this.productRepository = productRepository;
        this.cartService = cartService;
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

    // ---------------------------------------------------------
    // Recommended Products
    // ---------------------------------------------------------

    private static final Set<String> POPULAR_PRODUCT_NAMES = Set.of(
            "Milk", "Bread", "Rice (1 kg)", "Sugar (1 kg)",
            "Soft Drink", "Chocolate", "Shampoo", "Notebook"
    );

    public List<Product> getPopularProducts(Set<Long> excludeIds) {

        return productRepository.findByNameIn(POPULAR_PRODUCT_NAMES)
                .stream()
                .filter(p -> !excludeIds.contains(p.getId()))
                .collect(Collectors.toList());
    }

    public List<Product> getDailyPicks(int count, Set<Long> excludeIds) {

        List<Product> allProducts = productRepository.findAll()
                .stream()
                .filter(p -> !excludeIds.contains(p.getId()))
                .collect(Collectors.toList());

        long seed = LocalDate.now().toEpochDay();
        Collections.shuffle(allProducts, new Random(seed));

        return allProducts.stream()
                .limit(count)
                .collect(Collectors.toList());
    }

    public List<Product> getRecommendationsBasedOnCart(Collection<Product> cartProducts, int count) {

        if (cartProducts.isEmpty()) {
            return List.of();
        }

        Set<Long> excludeIds = cartProducts.stream()
                .map(Product::getId)
                .collect(Collectors.toSet());

        Set<String> categories = cartProducts.stream()
                .map(Product::getCategory)
                .collect(Collectors.toSet());

        return productRepository.findByCategoryIn(categories)
                .stream()
                .filter(p -> !excludeIds.contains(p.getId()))
                .distinct()
                .limit(count)
                .collect(Collectors.toList());
    }

    public List<Product> getSameCategoryRecommendations(String lastScannedBarcode, Set<Long> excludeIds, int count) {

        if (lastScannedBarcode == null || lastScannedBarcode.isBlank()) {
            return List.of();
        }

        Optional<Product> lastScanned = productRepository.findByBarcode(lastScannedBarcode);

        if (lastScanned.isEmpty()) {
            return List.of();
        }

        String category = lastScanned.get().getCategory();
        Long selfId = lastScanned.get().getId();

        return productRepository.findByCategory(category)
                .stream()
                .filter(p -> !p.getId().equals(selfId))
                .filter(p -> !excludeIds.contains(p.getId()))
                .limit(count)
                .collect(Collectors.toList());
    }

    public Map<String, List<Product>> getRecommendedProducts(String lastScannedBarcode) {

        Collection<CartItem> cartItems = cartService.getCart().getItems();

        Set<Long> cartProductIds = cartItems.stream()
                .map(item -> item.getProduct().getId())
                .collect(Collectors.toSet());

        List<Product> cartProducts = cartItems.stream()
                .map(CartItem::getProduct)
                .collect(Collectors.toList());

        Map<String, List<Product>> result = new LinkedHashMap<>();

        result.put("popular", getPopularProducts(cartProductIds));
        result.put("basedOnCart", getRecommendationsBasedOnCart(cartProducts, 6));
        result.put("sameCategory", getSameCategoryRecommendations(lastScannedBarcode, cartProductIds, 6));
        result.put("dailyPicks", getDailyPicks(4, cartProductIds));

        return result;
    }

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
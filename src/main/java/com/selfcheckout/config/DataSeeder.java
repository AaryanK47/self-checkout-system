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

        //Dairy (1–7)
        addProduct("Milk", "1", new BigDecimal("60.00"), 35, "Dairy");
        addProduct("Bread", "2", new BigDecimal("40.00"), 42, "Bakery");
        addProduct("Eggs (12 pcs)", "3", new BigDecimal("120.00"), 28, "Dairy");
        addProduct("Butter", "4", new BigDecimal("55.00"), 22, "Dairy");
        addProduct("Cheese", "5", new BigDecimal("180.00"), 18, "Dairy");
        addProduct("Curd", "6", new BigDecimal("35.00"), 30, "Dairy");
        addProduct("Paneer", "7", new BigDecimal("95.00"), 20, "Dairy");

        //Grocery (8–15)
        addProduct("Rice (1 kg)", "8", new BigDecimal("75.00"), 50, "Grocery");
        addProduct("Sugar (1 kg)", "9", new BigDecimal("48.00"), 40, "Grocery");
        addProduct("Salt (1 kg)", "10", new BigDecimal("25.00"), 60, "Grocery");
        addProduct("Tea Powder", "11", new BigDecimal("140.00"), 24, "Beverages");
        addProduct("Coffee Powder", "12", new BigDecimal("220.00"), 18, "Beverages");
        addProduct("Boost", "13", new BigDecimal("285.00"), 15, "Beverages");
        addProduct("Horlicks", "14", new BigDecimal("315.00"), 14, "Beverages");
        addProduct("Bournvita", "15", new BigDecimal("299.00"), 16, "Beverages");

        //Snacks (16–20)
        addProduct("Biscuits", "16", new BigDecimal("30.00"), 70, "Snacks");
        addProduct("Potato Chips", "17", new BigDecimal("20.00"), 80, "Snacks");
        addProduct("Chocolate", "18", new BigDecimal("80.00"), 45, "Snacks");
        addProduct("Namkeen", "19", new BigDecimal("55.00"), 35, "Snacks");
        addProduct("Popcorn", "20", new BigDecimal("65.00"), 30, "Snacks");

        //Fruits (21–30)
        addProduct("Apple", "21", new BigDecimal("180.00"), 45, "Fruits");
        addProduct("Banana", "22", new BigDecimal("60.00"), 70, "Fruits");
        addProduct("Orange", "23", new BigDecimal("120.00"), 40, "Fruits");
        addProduct("Mango", "24", new BigDecimal("150.00"), 35, "Fruits");
        addProduct("Grapes", "25", new BigDecimal("90.00"), 28, "Fruits");
        addProduct("Pomegranate", "26", new BigDecimal("220.00"), 20, "Fruits");
        addProduct("Watermelon", "27", new BigDecimal("80.00"), 18, "Fruits");
        addProduct("Papaya", "28", new BigDecimal("55.00"), 22, "Fruits");
        addProduct("Pineapple", "29", new BigDecimal("95.00"), 16, "Fruits");
        addProduct("Guava", "30", new BigDecimal("75.00"), 25, "Fruits");

        //Vegetables (31–40)
        addProduct("Tomato", "31", new BigDecimal("35.00"), 55, "Vegetables");
        addProduct("Potato", "32", new BigDecimal("40.00"), 80, "Vegetables");
        addProduct("Onion", "33", new BigDecimal("45.00"), 75, "Vegetables");
        addProduct("Carrot", "34", new BigDecimal("60.00"), 38, "Vegetables");
        addProduct("Beans", "35", new BigDecimal("70.00"), 30, "Vegetables");
        addProduct("Cabbage", "36", new BigDecimal("45.00"), 22, "Vegetables");
        addProduct("Cauliflower", "37", new BigDecimal("55.00"), 20, "Vegetables");
        addProduct("Spinach", "38", new BigDecimal("25.00"), 28, "Vegetables");
        addProduct("Brinjal", "39", new BigDecimal("48.00"), 26, "Vegetables");
        addProduct("Green Chilli", "40", new BigDecimal("30.00"), 40, "Vegetables");

        //Personal Care (41–50)
        addProduct("Shampoo", "41", new BigDecimal("250.00"), 20, "Personal Care");
        addProduct("Soap", "42", new BigDecimal("35.00"), 60, "Personal Care");
        addProduct("Toothpaste", "43", new BigDecimal("110.00"), 35, "Personal Care");
        addProduct("Toothbrush", "44", new BigDecimal("40.00"), 50, "Personal Care");
        addProduct("Hair Oil", "45", new BigDecimal("160.00"), 22, "Personal Care");
        addProduct("Face Wash", "46", new BigDecimal("180.00"), 18, "Personal Care");
        addProduct("Body Lotion", "47", new BigDecimal("220.00"), 16, "Personal Care");
        addProduct("Hand Wash", "48", new BigDecimal("90.00"), 30, "Personal Care");
        addProduct("Hand Sanitizer", "49", new BigDecimal("95.00"), 28, "Personal Care");
        addProduct("Shaving Cream", "50", new BigDecimal("140.00"), 20, "Personal Care");

        //Household (51–60)
        addProduct("Detergent Powder", "51", new BigDecimal("320.00"), 25, "Household");
        addProduct("Dishwash Liquid", "52", new BigDecimal("120.00"), 24, "Household");
        addProduct("Floor Cleaner", "53", new BigDecimal("180.00"), 20, "Household");
        addProduct("Glass Cleaner", "54", new BigDecimal("150.00"), 18, "Household");
        addProduct("Toilet Cleaner", "55", new BigDecimal("135.00"), 18, "Household");
        addProduct("Garbage Bags", "56", new BigDecimal("90.00"), 30, "Household");
        addProduct("Aluminium Foil", "57", new BigDecimal("110.00"), 22, "Household");
        addProduct("Plastic Wrap", "58", new BigDecimal("95.00"), 20, "Household");
        addProduct("Tissue Paper", "59", new BigDecimal("80.00"), 35, "Household");
        addProduct("Paper Towels", "60", new BigDecimal("140.00"), 18, "Household");

        //Beverages (61–70)
        addProduct("Mineral Water", "61", new BigDecimal("20.00"), 80, "Beverages");
        addProduct("Soft Drink", "62", new BigDecimal("45.00"), 50, "Beverages");
        addProduct("Orange Juice", "63", new BigDecimal("95.00"), 25, "Beverages");
        addProduct("Apple Juice", "64", new BigDecimal("110.00"), 22, "Beverages");
        addProduct("Energy Drink", "65", new BigDecimal("125.00"), 20, "Beverages");
        addProduct("Green Tea", "66", new BigDecimal("240.00"), 18, "Beverages");
        addProduct("Black Tea", "67", new BigDecimal("180.00"), 20, "Beverages");
        addProduct("Lemon Juice", "68", new BigDecimal("70.00"), 25, "Beverages");
        addProduct("Coconut Water", "69", new BigDecimal("50.00"), 30, "Beverages");
        addProduct("Cold Coffee", "70", new BigDecimal("85.00"), 24, "Beverages");

        //Packaged Foods (71–80)
        addProduct("Instant Noodles", "71", new BigDecimal("20.00"), 60, "Packaged Foods");
        addProduct("Pasta", "72", new BigDecimal("90.00"), 35, "Packaged Foods");
        addProduct("Macaroni", "73", new BigDecimal("85.00"), 30, "Packaged Foods");
        addProduct("Corn Flakes", "74", new BigDecimal("180.00"), 20, "Packaged Foods");
        addProduct("Oats", "75", new BigDecimal("160.00"), 22, "Packaged Foods");
        addProduct("Tomato Ketchup", "76", new BigDecimal("120.00"), 26, "Packaged Foods");
        addProduct("Mayonnaise", "77", new BigDecimal("170.00"), 18, "Packaged Foods");
        addProduct("Jam", "78", new BigDecimal("150.00"), 20, "Packaged Foods");
        addProduct("Peanut Butter", "79", new BigDecimal("220.00"), 16, "Packaged Foods");
        addProduct("Honey", "80", new BigDecimal("260.00"), 15, "Packaged Foods");

        //Spices (81 - 90)
        addProduct("Turmeric Powder", "81", new BigDecimal("45.00"), 40, "Spices");
        addProduct("Red Chilli Powder", "82", new BigDecimal("55.00"), 38, "Spices");
        addProduct("Coriander Powder", "83", new BigDecimal("48.00"), 35, "Spices");
        addProduct("Garam Masala", "84", new BigDecimal("75.00"), 30, "Spices");
        addProduct("Black Pepper", "85", new BigDecimal("95.00"), 22, "Spices");
        addProduct("Cumin Seeds", "86", new BigDecimal("80.00"), 25, "Spices");
        addProduct("Mustard Seeds", "87", new BigDecimal("40.00"), 28, "Spices");
        addProduct("Cardamom", "88", new BigDecimal("180.00"), 18, "Spices");
        addProduct("Cloves", "89", new BigDecimal("150.00"), 16, "Spices");
        addProduct("Cinnamon", "90", new BigDecimal("120.00"), 20, "Spices");

        //Pulses (91 - 96)
        addProduct("Toor Dal", "91", new BigDecimal("140.00"), 35, "Pulses");
        addProduct("Moong Dal", "92", new BigDecimal("130.00"), 32, "Pulses");
        addProduct("Urad Dal", "93", new BigDecimal("145.00"), 30, "Pulses");
        addProduct("Chana Dal", "94", new BigDecimal("125.00"), 28, "Pulses");
        addProduct("Rajma", "95", new BigDecimal("160.00"), 22, "Pulses");
        addProduct("Chickpeas", "96", new BigDecimal("120.00"), 30, "Pulses");

        //Grocery (97 - 98)
        addProduct("Cooking Oil (1 L)", "97", new BigDecimal("180.00"), 24, "Grocery");
        addProduct("Sunflower Oil (1 L)", "98", new BigDecimal("210.00"), 20, "Grocery");

        //Dairy (99)
        addProduct("Ghee (500 ml)", "99", new BigDecimal("320.00"), 18, "Dairy");

        //Grocery (100)
        addProduct("Wheat Flour (5 kg)", "100", new BigDecimal("280.00"), 26, "Grocery");
    }

    private void addProduct(String name,
                            String barcode,
                            BigDecimal price,
                            int stockQty,
                            String category) {

        if (!productRepository.existsByBarcode(barcode)) {

            Product product = new Product();

            product.setName(name);
            product.setBarcode(barcode);
            product.setPrice(price);
            product.setStockQty(stockQty);
            product.setCategory(category);

            productRepository.save(product);

        }

    }

}

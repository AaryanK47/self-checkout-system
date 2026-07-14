package com.selfcheckout.repository;

import com.selfcheckout.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.Collection;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByBarcode(String barcode);

    boolean existsByBarcode(String barcode);

    List<Product> findByNameIn(Collection<String> names);

    List<Product> findByCategory(String category);

    List<Product> findByCategoryIn(Collection<String> categories);

}
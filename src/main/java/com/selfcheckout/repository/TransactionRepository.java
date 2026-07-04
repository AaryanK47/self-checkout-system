package com.selfcheckout.repository;

import com.selfcheckout.entity.Transaction;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @EntityGraph(attributePaths = {
            "items",
            "items.product"
    })
    Optional<Transaction> findWithItemsById(Long id);

}
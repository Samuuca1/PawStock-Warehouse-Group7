package com.pawstock.pawstock_warehouse.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pawstock.pawstock_warehouse.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(
        value = """
            SELECT p
            FROM Product p
            JOIN FETCH p.brand b
            JOIN FETCH p.category c
            JOIN FETCH p.supplier s
            WHERE (:keyword IS NULL
                   OR LOWER(p.productName)
                   LIKE LOWER(CONCAT('%', :keyword, '%')))
              AND (:brandId IS NULL OR b.brandId = :brandId)
              AND (:categoryId IS NULL OR c.categoryId = :categoryId)
              AND (:petType IS NULL OR p.petType = :petType)
            """,
        countQuery = """
            SELECT COUNT(p)
            FROM Product p
            JOIN p.brand b
            JOIN p.category c
            JOIN p.supplier s
            WHERE (:keyword IS NULL
                   OR LOWER(p.productName)
                   LIKE LOWER(CONCAT('%', :keyword, '%')))
              AND (:brandId IS NULL OR b.brandId = :brandId)
              AND (:categoryId IS NULL OR c.categoryId = :categoryId)
              AND (:petType IS NULL OR p.petType = :petType)
            """
    )
    Page<Product> searchProducts(
            @Param("keyword") String keyword,
            @Param("brandId") Long brandId,
            @Param("categoryId") Long categoryId,
            @Param("petType") String petType,
            Pageable pageable);

    @Query("""
        SELECT p
        FROM Product p
        JOIN FETCH p.brand
        JOIN FETCH p.category
        JOIN FETCH p.supplier
        WHERE p.productId = :productId
        """)
    Optional<Product> findProductWithDetails(
            @Param("productId") Long productId);
}
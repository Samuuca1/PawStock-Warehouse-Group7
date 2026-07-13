package com.pawstock.pawstock_warehouse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pawstock.pawstock_warehouse.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByOrderByCategoryNameAsc();
}
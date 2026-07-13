package com.pawstock.pawstock_warehouse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pawstock.pawstock_warehouse.model.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    List<Brand> findAllByOrderByBrandNameAsc();
}
package com.pawstock.pawstock_warehouse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pawstock.pawstock_warehouse.model.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    List<Supplier> findAllByOrderBySupplierNameAsc();
}
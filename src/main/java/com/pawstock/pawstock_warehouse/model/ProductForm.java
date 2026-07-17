package com.pawstock.pawstock_warehouse.model;

import java.math.BigDecimal;

import jakarta.validation.constraints.*;

public class ProductForm {

    @NotBlank(message = "Product name is required.")
    @Size(min = 2, max = 100,
            message = "Product name must be between 2 and 100 characters.")
    private String productName;

    @Size(max = 255,
            message = "Description cannot exceed 255 characters.")
    private String description;

    @NotNull(message = "Price is required.")
    @DecimalMin(value = "0.01",
            message = "Price must be greater than zero.")
    private BigDecimal price;

    @NotBlank(message = "Pet type is required.")
    private String petType;

    @Size(max = 30,
            message = "Size cannot exceed 30 characters.")
    private String size;

    @NotNull(message = "Quantity is required.")
    @Min(value = 0,
            message = "Quantity cannot be negative.")
    private Integer quantity;

    @NotNull(message = "Brand is required.")
    private Long brandId;

    @NotNull(message = "Category is required.")
    private Long categoryId;

    @NotNull(message = "Supplier is required.")
    private Long supplierId;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {this.quantity = quantity;}

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }
}
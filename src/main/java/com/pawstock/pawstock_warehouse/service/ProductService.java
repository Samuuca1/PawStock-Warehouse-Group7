package com.pawstock.pawstock_warehouse.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pawstock.pawstock_warehouse.exception.ProductNotFoundException;
import com.pawstock.pawstock_warehouse.model.Brand;
import com.pawstock.pawstock_warehouse.model.Category;
import com.pawstock.pawstock_warehouse.model.Product;
import com.pawstock.pawstock_warehouse.model.ProductForm;
import com.pawstock.pawstock_warehouse.model.Supplier;
import com.pawstock.pawstock_warehouse.repository.BrandRepository;
import com.pawstock.pawstock_warehouse.repository.CategoryRepository;
import com.pawstock.pawstock_warehouse.repository.ProductRepository;
import com.pawstock.pawstock_warehouse.repository.SupplierRepository;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private static final int PAGE_SIZE = 6;

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;

    public ProductService(
            ProductRepository productRepository,
            BrandRepository brandRepository,
            CategoryRepository categoryRepository,
            SupplierRepository supplierRepository) {

        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
        this.supplierRepository = supplierRepository;
    }

    public Page<Product> searchProducts(
            String keyword,
            Long brandId,
            Long categoryId,
            String petType,
            int page,
            String sortField,
            String sortDirection) {

        String normalizedKeyword = normalizeText(keyword);
        String normalizedPetType = normalizeText(petType);
        String validSortField = validateSortField(sortField);

        Sort.Direction direction =
                "desc".equalsIgnoreCase(sortDirection)
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(
                Math.max(page, 0),
                PAGE_SIZE,
                Sort.by(direction, validSortField));

        return productRepository.searchProducts(
                normalizedKeyword,
                brandId,
                categoryId,
                normalizedPetType,
                pageable);
    }

    public Product getProductById(Long productId) {
        return productRepository.findProductWithDetails(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    public ProductForm getProductForm(Long productId) {
        Product product = getProductById(productId);
        ProductForm form = new ProductForm();

        form.setProductName(product.getProductName());
        form.setDescription(product.getDescription());
        form.setPrice(product.getPrice());
        form.setPetType(product.getPetType());
        form.setSize(product.getSize());
        form.setBrandId(product.getBrand().getBrandId());
        form.setCategoryId(product.getCategory().getCategoryId());
        form.setSupplierId(product.getSupplier().getSupplierId());

        return form;
    }

    @Transactional
    public Product createProduct(ProductForm form) {
        Product product = new Product();
        copyFormToProduct(form, product);
        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(Long productId, ProductForm form) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        copyFormToProduct(form, product);
        return productRepository.save(product);
    }

    @Transactional
    public String deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        String productName = product.getProductName();
        productRepository.delete(product);
        return productName;
    }

    public List<Brand> getAllBrands() {
        return brandRepository.findAllByOrderByBrandNameAsc();
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAllByOrderByCategoryNameAsc();
    }

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAllByOrderBySupplierNameAsc();
    }

    public long countProducts() {
        return productRepository.count();
    }

    public long countBrands() {
        return brandRepository.count();
    }

    public long countCategories() {
        return categoryRepository.count();
    }

    public long countSuppliers() {
        return supplierRepository.count();
    }

    private void copyFormToProduct(ProductForm form, Product product) {
        Brand brand = brandRepository.findById(form.getBrandId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid brand selected."));

        Category category = categoryRepository.findById(form.getCategoryId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid category selected."));

        Supplier supplier = supplierRepository.findById(form.getSupplierId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid supplier selected."));

        product.setProductName(form.getProductName().trim());
        product.setDescription(normalizeText(form.getDescription()));
        product.setPrice(form.getPrice());
        product.setPetType(form.getPetType().trim());
        product.setSize(normalizeText(form.getSize()));
        product.setBrand(brand);
        product.setCategory(category);
        product.setSupplier(supplier);
    }

    private String normalizeText(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return value.trim();
    }

    private String validateSortField(String sortField) {
        if (sortField == null) {
            return "productName";
        }

        return switch (sortField) {
            case "productName",
                 "price",
                 "petType",
                 "brand.brandName",
                 "category.categoryName",
                 "createdAt" -> sortField;
            default -> "productName";
        };
    }
}

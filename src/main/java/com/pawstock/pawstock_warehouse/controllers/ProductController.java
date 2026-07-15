package com.pawstock.pawstock_warehouse.controllers;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pawstock.pawstock_warehouse.model.Product;
import com.pawstock.pawstock_warehouse.model.ProductForm;
import com.pawstock.pawstock_warehouse.service.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {

    private static final List<String> PET_TYPES = List.of(
            "Dog",
            "Cat",
            "Fish",
            "Bird",
            "Small Animal",
            "Reptile"
    );

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String listProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long brandId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String petType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "productName") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection,
            Model model) {

        Page<Product> productPage = productService.searchProducts(
                keyword,
                brandId,
                categoryId,
                petType,
                page,
                sortField,
                sortDirection);

        model.addAttribute("productPage", productPage);
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("brands", productService.getAllBrands());
        model.addAttribute("categories", productService.getAllCategories());
        model.addAttribute("petTypes", PET_TYPES);
        model.addAttribute("keyword", keyword);
        model.addAttribute("selectedBrandId", brandId);
        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("selectedPetType", petType);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);

        return "products/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("productForm", new ProductForm());
        model.addAttribute("isEdit", false);
        addFormOptions(model);

        return "products/form";
    }

    @PostMapping
    public String createProduct(
            @Valid @ModelAttribute("productForm") ProductForm productForm,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", false);
            addFormOptions(model);
            return "products/form";
        }

        Product savedProduct = productService.createProduct(productForm);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Product was added successfully.");

        return "redirect:/products/" + savedProduct.getProductId();
    }

    @GetMapping("/{productId}")
    public String showProductDetails(
            @PathVariable Long productId,
            Model model) {

        model.addAttribute(
                "product",
                productService.getProductById(productId));

        return "products/details";
    }

    @GetMapping("/{productId}/edit")
    public String showEditForm(
            @PathVariable Long productId,
            Model model) {

        model.addAttribute(
                "productForm",
                productService.getProductForm(productId));
        model.addAttribute("productId", productId);
        model.addAttribute("isEdit", true);
        addFormOptions(model);

        return "products/form";
    }

    @PostMapping("/{productId}")
    public String updateProduct(
            @PathVariable Long productId,
            @Valid @ModelAttribute("productForm") ProductForm productForm,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("productId", productId);
            model.addAttribute("isEdit", true);
            addFormOptions(model);
            return "products/form";
        }

        productService.updateProduct(productId, productForm);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Product was updated successfully.");

        return "redirect:/products/" + productId;
    }

    @PostMapping("/{productId}/delete")
    public String deleteProduct(
            @PathVariable Long productId,
            RedirectAttributes redirectAttributes) {

        String productName = productService.deleteProduct(productId);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                productName + " was deleted successfully.");

        return "redirect:/products";
    }

    private void addFormOptions(Model model) {
        model.addAttribute("brands", productService.getAllBrands());
        model.addAttribute("categories", productService.getAllCategories());
        model.addAttribute("suppliers", productService.getAllSuppliers());
        model.addAttribute("petTypes", PET_TYPES);
    }
}

package com.pawstock.pawstock_warehouse.controllers;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pawstock.pawstock_warehouse.model.AppUser;
import com.pawstock.pawstock_warehouse.model.Brand;
import com.pawstock.pawstock_warehouse.model.Category;
import com.pawstock.pawstock_warehouse.model.Role;
import com.pawstock.pawstock_warehouse.model.Supplier;
import com.pawstock.pawstock_warehouse.repository.BrandRepository;
import com.pawstock.pawstock_warehouse.repository.CategoryRepository;
import com.pawstock.pawstock_warehouse.repository.SupplierRepository;
import com.pawstock.pawstock_warehouse.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;

    public AdminController(
            UserService userService,
            BrandRepository brandRepository,
            CategoryRepository categoryRepository,
            SupplierRepository supplierRepository) {

        this.userService = userService;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
        this.supplierRepository = supplierRepository;
    }

    // =========================================================
    // Admin Dashboard / User Management
    // =========================================================

    @GetMapping
    public String dashboard(Model model) {

        model.addAttribute(
                "users",
                userService.getAllUsers());

        model.addAttribute(
                "roles",
                Role.values());

        model.addAttribute(
                "activePage",
                "admin");

        return "admin/dashboard";
    }

    /**
     * Updates a user's role.
     * The signed-in administrator cannot change their own role.
     */
    @PostMapping("/users/{userId}/role")
    public String updateRole(
            @PathVariable Long userId,
            @RequestParam("role") Role role,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        try {
            AppUser user = userService.findById(userId);

            if (user.getUsername().equalsIgnoreCase(
                    authentication.getName())) {

                redirectAttributes.addFlashAttribute(
                        "errorMessage",
                        "You cannot change the role of your own administrator account.");

                return "redirect:/admin";
            }

            AppUser updatedUser =
                    userService.updateRole(userId, role);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    updatedUser.getUsername()
                            + "'s role was successfully updated to "
                            + updatedUser.getRole().name()
                            + ".");

        } catch (IllegalArgumentException exception) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    exception.getMessage());
        }

        return "redirect:/admin";
    }

    /**
     * Deletes a user.
     * The signed-in administrator cannot delete their own account.
     */
    @PostMapping("/users/{userId}/delete")
    public String deleteUser(
            @PathVariable Long userId,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        try {
            AppUser user = userService.findById(userId);

            if (user.getUsername().equalsIgnoreCase(
                    authentication.getName())) {

                redirectAttributes.addFlashAttribute(
                        "errorMessage",
                        "You cannot delete your own administrator account.");

                return "redirect:/admin";
            }

            String username = user.getUsername();

            userService.deleteUser(userId);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    username + " was deleted successfully.");

        } catch (IllegalArgumentException exception) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    exception.getMessage());
        }

        return "redirect:/admin";
    }

    // =========================================================
    // Brand Management
    // =========================================================

    @GetMapping("/brands")
    public String showBrands(Model model) {

        model.addAttribute(
                "brands",
                brandRepository.findAll());

        model.addAttribute(
                "activePage",
                "admin");

        return "admin/brands";
    }

    @PostMapping("/brands/add")
    public String addBrand(
            @RequestParam String brandName,
            @RequestParam(required = false) String country,
            RedirectAttributes redirectAttributes) {

        if (brandName == null || brandName.isBlank()) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Brand name is required.");

            return "redirect:/admin/brands";
        }

        Brand brand = new Brand();

        brand.setBrandName(brandName.trim());

        if (country != null) {
            brand.setCountry(country.trim());
        }

        brandRepository.save(brand);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Brand was added successfully.");

        return "redirect:/admin/brands";
    }

    @PostMapping("/brands/{brandId}/update")
    public String updateBrand(
            @PathVariable Long brandId,
            @RequestParam String brandName,
            @RequestParam(required = false) String country,
            RedirectAttributes redirectAttributes) {

        if (brandName == null || brandName.isBlank()) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Brand name is required.");

            return "redirect:/admin/brands";
        }

        Brand brand = brandRepository.findById(brandId)
                .orElse(null);

        if (brand == null) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Brand was not found.");

            return "redirect:/admin/brands";
        }

        brand.setBrandName(brandName.trim());

        if (country != null) {
            brand.setCountry(country.trim());
        }

        brandRepository.save(brand);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Brand was updated successfully.");

        return "redirect:/admin/brands";
    }

    @PostMapping("/brands/{brandId}/delete")
    public String deleteBrand(
            @PathVariable Long brandId,
            RedirectAttributes redirectAttributes) {

        try {
            brandRepository.deleteById(brandId);
            brandRepository.flush();

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Brand was deleted successfully.");

        } catch (DataIntegrityViolationException exception) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "This brand cannot be deleted because it is currently used by a product.");
        }

        return "redirect:/admin/brands";
    }

    // =========================================================
    // Category Management
    // =========================================================

    @GetMapping("/categories")
    public String showCategories(Model model) {

        model.addAttribute(
                "categories",
                categoryRepository.findAll());

        model.addAttribute(
                "activePage",
                "admin");

        return "admin/categories";
    }

    @PostMapping("/categories/add")
    public String addCategory(
            @RequestParam String categoryName,
            @RequestParam(required = false) String description,
            RedirectAttributes redirectAttributes) {

        if (categoryName == null || categoryName.isBlank()) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Category name is required.");

            return "redirect:/admin/categories";
        }

        Category category = new Category();

        category.setCategoryName(categoryName.trim());

        if (description != null) {
            category.setDescription(description.trim());
        }

        categoryRepository.save(category);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Category was added successfully.");

        return "redirect:/admin/categories";
    }

    @PostMapping("/categories/{categoryId}/update")
    public String updateCategory(
            @PathVariable Long categoryId,
            @RequestParam String categoryName,
            @RequestParam(required = false) String description,
            RedirectAttributes redirectAttributes) {

        if (categoryName == null || categoryName.isBlank()) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Category name is required.");

            return "redirect:/admin/categories";
        }

        Category category = categoryRepository
                .findById(categoryId)
                .orElse(null);

        if (category == null) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Category was not found.");

            return "redirect:/admin/categories";
        }

        category.setCategoryName(categoryName.trim());

        if (description != null) {
            category.setDescription(description.trim());
        }

        categoryRepository.save(category);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Category was updated successfully.");

        return "redirect:/admin/categories";
    }

    @PostMapping("/categories/{categoryId}/delete")
    public String deleteCategory(
            @PathVariable Long categoryId,
            RedirectAttributes redirectAttributes) {

        try {
            categoryRepository.deleteById(categoryId);
            categoryRepository.flush();

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Category was deleted successfully.");

        } catch (DataIntegrityViolationException exception) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "This category cannot be deleted because it is currently used by a product.");
        }

        return "redirect:/admin/categories";
    }

    // =========================================================
    // Supplier Management
    // =========================================================

    @GetMapping("/suppliers")
    public String showSuppliers(Model model) {

        model.addAttribute(
                "suppliers",
                supplierRepository.findAll());

        model.addAttribute(
                "activePage",
                "admin");

        return "admin/suppliers";
    }

    @PostMapping("/suppliers/add")
    public String addSupplier(
            @RequestParam String supplierName,
            @RequestParam String email,
            @RequestParam(required = false) String phone,
            RedirectAttributes redirectAttributes) {

        if (supplierName == null || supplierName.isBlank()) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Supplier name is required.");

            return "redirect:/admin/suppliers";
        }

        if (email == null || email.isBlank()) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Supplier email is required.");

            return "redirect:/admin/suppliers";
        }

        Supplier supplier = new Supplier();

        supplier.setSupplierName(supplierName.trim());
        supplier.setEmail(email.trim());

        if (phone != null) {
            supplier.setPhone(phone.trim());
        }

        try {
            supplierRepository.saveAndFlush(supplier);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Supplier was added successfully.");

        } catch (DataIntegrityViolationException exception) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "That supplier email is already in use.");
        }

        return "redirect:/admin/suppliers";
    }

    @PostMapping("/suppliers/{supplierId}/update")
    public String updateSupplier(
            @PathVariable Long supplierId,
            @RequestParam String supplierName,
            @RequestParam String email,
            @RequestParam(required = false) String phone,
            RedirectAttributes redirectAttributes) {

        if (supplierName == null || supplierName.isBlank()) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Supplier name is required.");

            return "redirect:/admin/suppliers";
        }

        if (email == null || email.isBlank()) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Supplier email is required.");

            return "redirect:/admin/suppliers";
        }

        Supplier supplier = supplierRepository
                .findById(supplierId)
                .orElse(null);

        if (supplier == null) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Supplier was not found.");

            return "redirect:/admin/suppliers";
        }

        supplier.setSupplierName(supplierName.trim());
        supplier.setEmail(email.trim());

        if (phone != null) {
            supplier.setPhone(phone.trim());
        }

        try {
            supplierRepository.saveAndFlush(supplier);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Supplier was updated successfully.");

        } catch (DataIntegrityViolationException exception) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "That supplier email is already in use.");
        }

        return "redirect:/admin/suppliers";
    }

    @PostMapping("/suppliers/{supplierId}/delete")
    public String deleteSupplier(
            @PathVariable Long supplierId,
            RedirectAttributes redirectAttributes) {

        try {
            supplierRepository.deleteById(supplierId);
            supplierRepository.flush();

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Supplier was deleted successfully.");

        } catch (DataIntegrityViolationException exception) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "This supplier cannot be deleted because it is currently used by a product.");
        }

        return "redirect:/admin/suppliers";
    }
}
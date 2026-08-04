package com.pawstock.pawstock_warehouse.controllers;

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
import com.pawstock.pawstock_warehouse.model.Role;
import com.pawstock.pawstock_warehouse.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Displays the admin-only user management dashboard.
     */
    @GetMapping
    public String dashboard(Model model) {

        model.addAttribute(
                "users",
                userService.getAllUsers()
        );

        model.addAttribute(
                "roles",
                Role.values()
        );

        model.addAttribute(
                "activePage",
                "admin"
        );

        return "admin/dashboard";
    }

    /**
     * Updates the selected user's role.
     *
     * The currently signed-in administrator cannot change
     * their own role.
     */
    @PostMapping("/users/{userId}/role")
    public String updateRole(
            @PathVariable Long userId,
            @RequestParam("role") Role role,
            Authentication authentication,
            RedirectAttributes redirectAttributes
    ) {

        try {
            AppUser user = userService.findById(userId);

            // Prevent the current administrator from changing
            // their own role and losing access to the admin area.
            if (user.getUsername().equalsIgnoreCase(
                    authentication.getName())) {

                redirectAttributes.addFlashAttribute(
                        "errorMessage",
                        "You cannot change the role of your own administrator account."
                );

                return "redirect:/admin";
            }

            AppUser updatedUser =
                    userService.updateRole(userId, role);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    updatedUser.getUsername()
                            + "'s role was successfully updated to "
                            + updatedUser.getRole().name()
                            + "."
            );

        } catch (IllegalArgumentException exception) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    exception.getMessage()
            );
        }

        return "redirect:/admin";
    }

    /**
     * Deletes the selected user account.
     *
     * The currently signed-in administrator cannot delete
     * their own account.
     */
    @PostMapping("/users/{userId}/delete")
    public String deleteUser(
            @PathVariable Long userId,
            Authentication authentication,
            RedirectAttributes redirectAttributes
    ) {

        try {
            AppUser user = userService.findById(userId);

            // Prevent the current administrator from deleting
            // their own account.
            if (user.getUsername().equalsIgnoreCase(
                    authentication.getName())) {

                redirectAttributes.addFlashAttribute(
                        "errorMessage",
                        "You cannot delete your own administrator account."
                );

                return "redirect:/admin";
            }

            String username = user.getUsername();

            userService.deleteUser(userId);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    username + " was deleted successfully."
            );

        } catch (IllegalArgumentException exception) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    exception.getMessage()
            );
        }

        return "redirect:/admin";
    }
}
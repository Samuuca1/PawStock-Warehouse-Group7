package com.pawstock.pawstock_warehouse.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.pawstock.pawstock_warehouse.model.Role;
import com.pawstock.pawstock_warehouse.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

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

    @PostMapping("/users/{userId}/role")
    public String updateRole(
            @PathVariable Long userId,
            @RequestParam Role role
    ) {

        userService.updateRole(userId, role);

        return "redirect:/admin";
    }

    @PostMapping("/users/{userId}/delete")
    public String deleteUser(@PathVariable Long userId) {

        userService.deleteUser(userId);
        return "redirect:/admin";
    }
}
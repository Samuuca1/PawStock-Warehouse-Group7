package com.pawstock.pawstock_warehouse.controllers;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.pawstock.pawstock_warehouse.model.RegistrationForm;
import com.pawstock.pawstock_warehouse.service.UserService;

@Controller
public class AuthenticationController {

    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Displays the custom login page.
     */
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    /**
     * Displays the user registration form.
     */
    @GetMapping("/register")
    public String showRegistrationPage(Model model) {

        model.addAttribute(
                "registrationForm",
                new RegistrationForm()
        );

        return "register";
    }

    /**
     * Processes the submitted registration form.
     */
    @PostMapping("/register")
    public String registerUser(
            @Valid
            @ModelAttribute("registrationForm")
            RegistrationForm registrationForm,
            BindingResult bindingResult,
            Model model
    ) {

        // Return to the form when validation fails
        if (bindingResult.hasErrors()) {
            return "register";
        }

        try {
            userService.registerUser(registrationForm);
        } catch (IllegalArgumentException exception) {

            // Display errors such as duplicate username,
            // duplicate email, or mismatched passwords
            model.addAttribute(
                    "registrationError",
                    exception.getMessage()
            );

            return "register";
        }

        // Redirect to login after successful registration
        return "redirect:/login?registered";
    }

    /**
     * Displays the page shown when a user does not have
     * permission to access a protected resource.
     */
    @GetMapping("/access-denied")
    public String showAccessDeniedPage() {
        return "access-denied";
    }
}
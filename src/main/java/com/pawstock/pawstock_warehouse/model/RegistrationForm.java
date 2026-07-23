package com.pawstock.pawstock_warehouse.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegistrationForm {

    @NotBlank(message = "Full name is required.")
    @Size(
            min = 2,
            max = 100,
            message = "Full name must be between 2 and 100 characters."
    )
    private String fullName;

    @NotBlank(message = "Username is required.")
    @Size(
            min = 3,
            max = 50,
            message = "Username must be between 3 and 50 characters."
    )
    private String username;

    @NotBlank(message = "Email is required.")
    @Email(message = "Enter a valid email address.")
    @Size(
            max = 100,
            message = "Email cannot exceed 100 characters."
    )
    private String email;

    @NotBlank(message = "Password is required.")
    @Size(
            min = 8,
            max = 72,
            message = "Password must be between 8 and 72 characters."
    )
    private String password;

    @NotBlank(message = "Please confirm your password.")
    private String confirmPassword;

    public boolean passwordsMatch() {
        return password != null && password.equals(confirmPassword);
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
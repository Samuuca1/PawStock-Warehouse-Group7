package com.pawstock.pawstock_warehouse.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.pawstock.pawstock_warehouse.model.AppUser;
import com.pawstock.pawstock_warehouse.model.Role;
import com.pawstock.pawstock_warehouse.repository.UserRepository;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initializeUsers(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            createUserIfNotExists(
                    userRepository,
                    passwordEncoder,
                    "System Administrator",
                    "admin",
                    "admin@pawstock.com",
                    "Admin123!",
                    Role.ADMIN
            );

            createUserIfNotExists(
                    userRepository,
                    passwordEncoder,
                    "Warehouse Staff",
                    "staff",
                    "staff@pawstock.com",
                    "Staff123!",
                    Role.STAFF
            );

            createUserIfNotExists(
                    userRepository,
                    passwordEncoder,
                    "Demo Customer",
                    "customer",
                    "customer@pawstock.com",
                    "Customer123!",
                    Role.CUSTOMER
            );
        };
    }

    /**
     * Creates a user only if the username does not already exist.
     */
    private void createUserIfNotExists(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            String fullName,
            String username,
            String email,
            String password,
            Role role) {

        if (userRepository.existsByUsernameIgnoreCase(username)) {
            return;
        }

        AppUser user = new AppUser();

        user.setFullName(fullName);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        user.setEnabled(true);

        userRepository.save(user);

        System.out.println(role + " account created: " + username);
    }
}
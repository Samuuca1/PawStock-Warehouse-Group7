package com.pawstock.pawstock_warehouse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    /**
     * Uses BCrypt to securely hash user passwords.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http
                // ==========================================
                // Authorization rules
                // ==========================================
                .authorizeHttpRequests(authorize -> authorize

                        // ------------------------------------------
                        // Public pages
                        // ------------------------------------------
                        .requestMatchers(
                                "/",
                                "/about",
                                "/warehouse",
                                "/roadmap",
                                "/register",
                                "/login",
                                "/access-denied",
                                "/error",

                                // Static resources
                                "/css/**",
                                "/images/**",
                                "/media/**",
                                "/js/**",
                                "/webjars/**",
                                "/favicon.ico",

                                // H2 Console - development only
                                "/h2-console/**"
                        ).permitAll()

                        // ------------------------------------------
                        // Admin pages
                        // ------------------------------------------
                        .requestMatchers("/admin/**")
                        .hasRole("ADMIN")

                        // ------------------------------------------
                        // Delete product
                        // Only ADMIN
                        // ------------------------------------------
                        .requestMatchers(
                                HttpMethod.POST,
                                "/products/*/delete"
                        ).hasRole("ADMIN")

                        // ------------------------------------------
                        // Create product form
                        // STAFF and ADMIN
                        // ------------------------------------------
                        .requestMatchers(
                                HttpMethod.GET,
                                "/products/new"
                        ).hasAnyRole("STAFF", "ADMIN")

                        // ------------------------------------------
                        // Submit new product
                        // STAFF and ADMIN
                        // ------------------------------------------
                        .requestMatchers(
                                HttpMethod.POST,
                                "/products"
                        ).hasAnyRole("STAFF", "ADMIN")

                        // ------------------------------------------
                        // Edit product form
                        // STAFF and ADMIN
                        // ------------------------------------------
                        .requestMatchers(
                                HttpMethod.GET,
                                "/products/*/edit"
                        ).hasAnyRole("STAFF", "ADMIN")

                        // ------------------------------------------
                        // Submit product update
                        // STAFF and ADMIN
                        // ------------------------------------------
                        .requestMatchers(
                                HttpMethod.POST,
                                "/products/*"
                        ).hasAnyRole("STAFF", "ADMIN")

                        // ------------------------------------------
                        // Public product browsing
                        // Guests can view list and details
                        // ------------------------------------------
                        .requestMatchers(
                                HttpMethod.GET,
                                "/products",
                                "/products/*"
                        ).permitAll()

                        // Everything else requires login
                        .anyRequest()
                        .authenticated()
                )

                // ==========================================
                // H2 Console support
                // ==========================================
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**")
                )

                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())
                )

                // ==========================================
                // Login
                // ==========================================
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/products", true)
                        .failureUrl("/login?error")
                        .permitAll()
                )

                // ==========================================
                // Logout
                // ==========================================
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )

                // ==========================================
                // Access denied
                // ==========================================
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/access-denied")
                );

        return http.build();
    }
}
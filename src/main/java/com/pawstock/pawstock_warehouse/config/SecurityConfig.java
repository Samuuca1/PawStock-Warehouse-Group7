/**
     * Temporary configuration for Deliverable 1.
     *
     * Deliverable 2 will replace this with login,
     * registration, password encryption and roles.
     */

package com.pawstock.pawstock_warehouse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().permitAll()
            )
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**")
            )
            .headers(headers -> headers
                .frameOptions(frameOptions ->
                    frameOptions.sameOrigin()
                )
            )
            .formLogin(formLogin ->
                formLogin.disable()
            )
            .httpBasic(httpBasic ->
                httpBasic.disable()
            );

        return http.build();
    }
}
package com.pawstock.pawstock_warehouse.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pawstock.pawstock_warehouse.model.AppUser;
import com.pawstock.pawstock_warehouse.model.RegistrationForm;
import com.pawstock.pawstock_warehouse.model.Role;
import com.pawstock.pawstock_warehouse.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public AppUser registerUser(RegistrationForm form) {

        String username = form.getUsername().trim();
        String email = form.getEmail().trim().toLowerCase();

        if (userRepository.existsByUsernameIgnoreCase(username)) {
            throw new IllegalArgumentException(
                    "That username is already taken."
            );
        }

        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new IllegalArgumentException(
                    "That email address is already registered."
            );
        }

        if (!form.passwordsMatch()) {
            throw new IllegalArgumentException(
                    "Passwords do not match."
            );
        }

        AppUser user = new AppUser();

        user.setFullName(form.getFullName().trim());
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(
                passwordEncoder.encode(form.getPassword())
        );
        user.setRole(Role.CUSTOMER);
        user.setEnabled(true);

        return userRepository.save(user);
    }

    public AppUser findByUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "User was not found."
                        )
                );
    }

    public AppUser findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "User was not found."
                        )
                );
    }

    public List<AppUser> getAllUsers() {
        return userRepository.findAllByOrderByUsernameAsc();
    }

    @Transactional
    public AppUser updateRole(
            Long userId,
            Role role
    ) {
        AppUser user = findById(userId);
        user.setRole(role);

        return userRepository.save(user);
    }

    @Transactional
    public AppUser updateEnabledStatus(
            Long userId,
            boolean enabled
    ) {
        AppUser user = findById(userId);
        user.setEnabled(enabled);

        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        AppUser user = findById(userId);
        userRepository.delete(user);
    }

    public long countUsers() {
        return userRepository.count();
    }
}
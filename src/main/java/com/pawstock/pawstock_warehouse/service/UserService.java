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

    /**
     * Registers a new user with the CUSTOMER role.
     */
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

        // Store only the BCrypt-encoded password.
        user.setPassword(
                passwordEncoder.encode(form.getPassword())
        );

        // Public registration always creates a customer.
        user.setRole(Role.CUSTOMER);
        user.setEnabled(true);

        return userRepository.save(user);
    }

    /**
     * Finds a user by username.
     */
    public AppUser findByUsername(String username) {

        return userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "User was not found."
                        )
                );
    }

    /**
     * Finds a user by database ID.
     */
    public AppUser findById(Long userId) {

        return userRepository.findById(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "User was not found."
                        )
                );
    }

    /**
     * Returns all users ordered by username.
     */
    public List<AppUser> getAllUsers() {
        return userRepository.findAllByOrderByUsernameAsc();
    }

    /**
     * Updates and saves a user's role.
     */
    @Transactional
    public AppUser updateRole(
            Long userId,
            Role role
    ) {

        if (role == null) {
            throw new IllegalArgumentException(
                    "Please select a valid user role."
            );
        }

        AppUser user = findById(userId);

        user.setRole(role);

        // Flush writes the role change to H2 Database
        return userRepository.saveAndFlush(user);
    }

    /**
     * Enables or disables a user account.
     * (for future function).
     */
    @Transactional
    public AppUser updateEnabledStatus(
            Long userId,
            boolean enabled
    ) {

        AppUser user = findById(userId);

        user.setEnabled(enabled);

        return userRepository.saveAndFlush(user);
    }

    /**
     * Deletes a user account.
     */
    @Transactional
    public void deleteUser(Long userId) {

        AppUser user = findById(userId);

        userRepository.delete(user);
        userRepository.flush();
    }

    public long countUsers() {
        return userRepository.count();
    }
}
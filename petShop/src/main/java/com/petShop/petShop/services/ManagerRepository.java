package com.petShop.petShop.services;

import com.petShop.petShop.models.Cashier;
import com.petShop.petShop.models.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {

    // Find a cashier by email (for unique validation or updates)
    Optional<Cashier> findByEmail(String email);

    // Search cashiers by name (case-insensitive partial match)
    List<Cashier> findByNameContainingIgnoreCase(String name);

    // Search cashiers by name or email (case-insensitive)
    List<Cashier> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email);

    Manager findByUsername(String username); // Find manager by username
}
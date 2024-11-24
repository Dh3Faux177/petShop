package com.petShop.petShop.services;

import com.petShop.petShop.models.Cashier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CashierRepository extends JpaRepository<Cashier, Integer> {

    List<Cashier> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email);

    Cashier findByUsername(String username);  // Find cashier by username

}
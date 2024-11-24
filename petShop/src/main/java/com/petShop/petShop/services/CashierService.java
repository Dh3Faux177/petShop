package com.petShop.petShop.services;

import com.petShop.petShop.models.Cashier;
import com.petShop.petShop.models.Role;
import com.petShop.petShop.services.CashierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CashierService {

    @Autowired
    private CashierRepository cashierRepository;

    // Fetch all cashiers
    public List<Cashier> findAllCashiers() {
        return cashierRepository.findAll();
    }

    // Save a new cashier
    public void saveCashier(Cashier cashier) {
        cashierRepository.save(cashier);
    }

    // Find a cashier by ID
    public Cashier findCashierById(int id) {
        return cashierRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cashier not found with ID: " + id));
    }

    // Update an existing cashier
    public void updateCashier(int id, Cashier updatedCashier) {
        Cashier existingCashier = findCashierById(id);
        existingCashier.setName(updatedCashier.getName());
        existingCashier.setUsername(updatedCashier.getUsername());
        existingCashier.setEmail(updatedCashier.getEmail());
        existingCashier.setPhone(updatedCashier.getPhone());
        cashierRepository.save(existingCashier);
    }

    // Delete a cashier
    public void deleteCashier(int id) {
        Cashier cashier = findCashierById(id);
        cashierRepository.delete(cashier);
    }

    // Search cashiers by name or email
    public List<Cashier> searchByNameOrEmail(String query) {
        return cashierRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(query, query);
    }

    public void registerCashier(Cashier cashier) {
        // Set the default role
        //cashier.setRole(Role.CASHIER);  // Setting to CASHIER by default

        // Save the cashier to the repository
        cashierRepository.save(cashier);
    }

    // Authenticate cashier based on username and password
    public Cashier authenticateCashier(String username, String password) {
        Cashier cashier = cashierRepository.findByUsername(username);
        if (cashier != null && cashier.getPassword().equals(password)) { // Plain text password comparison
            return cashier;
        }
        return null;
    }
}
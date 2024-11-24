package com.petShop.petShop.services;

import com.petShop.petShop.models.Cashier;
import com.petShop.petShop.models.Manager;
import com.petShop.petShop.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CashierRepository cashierRepository;

    @Autowired
    private ManagerRepository managerRepository;

    // Cashier Management

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
        return cashierRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cashier not found with ID: " + id));
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

    // Search for cashiers by name or email
    public List<Cashier> searchCashiersByNameOrEmail(String query) {
        return cashierRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(query, query);
    }

    // Authenticate manager based on username and password
    public Manager authenticateManager(String username, String password) {
        Manager manager = managerRepository.findByUsername(username);
        if (manager != null && manager.getPassword().equals(password)) { // Plain text password comparison
            return manager;
        }
        return null;
    }
}

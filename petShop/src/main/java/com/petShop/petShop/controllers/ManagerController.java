package com.petShop.petShop.controllers;

import com.petShop.petShop.models.*;
import com.petShop.petShop.services.CashierRepository;
import com.petShop.petShop.services.CashierService;
import com.petShop.petShop.services.ManagerRepository;
import com.petShop.petShop.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ManagerController {

    private ProductService productService;
    private CashierRepository cashierRepository;
    private CashierService cashierService;
    private ManagerRepository managerRepository;

    @Autowired
    public ManagerController(ProductService productService, CashierRepository cashierRepository,
                             CashierService cashierService, ManagerRepository managerRepository) {
        this.productService = productService;
        this.cashierRepository = cashierRepository;
        this.cashierService = cashierService;
        this.managerRepository = managerRepository;
    }

    // Display Cashier List
    @GetMapping("/c-list")
    public String showCashierList(Model model) {
        model.addAttribute("cashiers", cashierRepository.findAll());
        return "products/list-cashier";
    }

    // Display Create Cashier Form
    @GetMapping("/c-create")
    public String showCreateCashierForm(Model model) {
        CashierDto cashierDto = new CashierDto();
        model.addAttribute("cashierDto", cashierDto);
        return "products/create-cashier";
    }

    // Handle Create Cashier Form Submission
    @PostMapping("/c-create")
    public String createCashier(@Valid @ModelAttribute("cashierDto") CashierDto cashierDto, BindingResult result, Model model) {

        // Manually check if the password is null or empty before proceeding with validation
        if (cashierDto.getPassword() == null || cashierDto.getPassword().isEmpty()) {
            // Handle password validation (maybe show an error and redirect to an error page)
            model.addAttribute("passwordError", "Password is required");
            return "products/create-cashier"; // Redirect back to the form page with the error message
        }

        // Check if there are validation errors from the @Valid annotation
        if (result.hasErrors()) {
            // Return the form with errors
            return "products/create-cashier";
        }

        // If validation passed, create and save a new Cashier
        Cashier cashier = new Cashier();
        cashier.setName(cashierDto.getName());
        cashier.setUsername(cashierDto.getUsername());
        cashier.setEmail(cashierDto.getEmail());
        cashier.setPhone(cashierDto.getPhone());
        cashier.setPassword(cashierDto.getPassword()); // Ensure password is set for the cashier
        // Optional: Set the role if needed
        // cashier.setRole(Role.CASHIER); // Default role

        cashierRepository.save(cashier); // Save the Cashier to the database

        // Redirect to the cashier list page after saving
        return "redirect:/products/c-list";
    }

    // Display Edit Cashier Form
    @GetMapping("/c-edit/{id}")
    public String showEditCashierForm(@PathVariable Integer id, Model model) {
        Optional<Cashier> cashierOpt = cashierRepository.findById(id);
        if (cashierOpt.isEmpty()) {
            model.addAttribute("error", "Cashier with ID " + id + " not found");
            return "redirect:/products/c-list"; // Redirect to cashier list page if not found
        }

        Cashier cashier = cashierOpt.get();
        CashierDto cashierDto = new CashierDto();
        cashierDto.setName(cashier.getName());
        cashierDto.setUsername(cashier.getUsername());
        cashierDto.setEmail(cashier.getEmail());
        cashierDto.setPhone(cashier.getPhone());

        model.addAttribute("cashierDto", cashierDto);
        model.addAttribute("cashierId", id);

        return "products/edit-cashier"; // Correct view for editing cashier
    }


    // Handle Edit Cashier Form Submission
    @PostMapping("/c-edit/{id}")
    public String editCashier(@PathVariable Integer id, @Valid @ModelAttribute CashierDto cashierDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("cashierId", id);
            return "products/edit-cashier";
        }

        // Update Cashier Details
        Cashier cashier = cashierRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Cashier ID: " + id));
        cashier.setName(cashierDto.getName());
        cashier.setUsername(cashierDto.getUsername());
        cashier.setEmail(cashierDto.getEmail());
        cashier.setPhone(cashierDto.getPhone());

        cashierRepository.save(cashier);

        return "redirect:/products/c-list";
    }

    // Delete a Cashier
    @PostMapping("/c-delete/{id}")
    public String deleteCashier(@PathVariable int id, RedirectAttributes redirectAttributes) {
        Optional<Cashier> cashierOpt = cashierRepository.findById(id);

        if (cashierOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Cashier with ID " + id + " does not exist.");
            return "redirect:/products/c-list"; // Redirect with error message
        }

        cashierRepository.delete(cashierOpt.get());
        redirectAttributes.addFlashAttribute("success", "Cashier deleted successfully.");
        return "redirect:/products/c-list";
    }


    @GetMapping("/calculation")
    public String showCalculationPage(Model model) {
        // Fetch all products from the database
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "purchase-product"; // The name of the HTML template
    }
}

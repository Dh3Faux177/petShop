package com.petShop.petShop.controllers;

import com.petShop.petShop.models.Cashier;
import com.petShop.petShop.models.Product;
import com.petShop.petShop.services.CashierRepository;
import com.petShop.petShop.services.ProductRepository;
import com.petShop.petShop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cashier")
public class CashierController {

    @Autowired
    private ProductService productService;
    private CashierRepository cashierRepository;
    private ProductRepository repo;

    @GetMapping({"/index-c"})
    public String showProductList(Model model){
        List<Product> products = repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("products",products);
        return "products/index-cashier";
    }

    @GetMapping("/view-cashier/{id}")
    public String viewProduct(@PathVariable int id, Model model) {
        Product product = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + id));
        model.addAttribute("product", product);
        return "products/view-product-cashier";
    }

    // Search products by name or category
    @GetMapping("/search-cashier")
    public String searchProducts(@RequestParam("query") String query, Model model) {
        List<Product> products = productService.searchByNameOrCategory(query);
        model.addAttribute("products", products);
        return "products/index-cashier"; // Display results on the same template

    }

    // Display calculation page
    @GetMapping("/calculate-c")
    public String showCalculationPage(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "products/purchase-product-cashier";
    }

    @PostMapping("/calculate-cashier")
    public String handlePurchase(@RequestParam Map<Integer, Integer> quantities, RedirectAttributes redirectAttributes) {
        System.out.println("Received quantities: " + quantities);

        try {
            quantities.forEach((productId, quantity) -> {
                System.out.println("Processing productId: " + productId + ", quantity: " + quantity);
                if (quantity > 0) {
                    productService.updateProductQuantity(productId, quantity);
                }
            });

            redirectAttributes.addFlashAttribute("successMessage", "Purchase completed successfully!");
        } catch (Exception e) {
            System.out.println("Error during purchase: " + e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Error processing purchase: " + e.getMessage());
        }

        return "redirect:/products/calculate";
    }
}

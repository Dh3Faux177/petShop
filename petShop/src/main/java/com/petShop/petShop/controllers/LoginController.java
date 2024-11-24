package com.petShop.petShop.controllers;

import com.petShop.petShop.models.Cashier;
import com.petShop.petShop.models.Manager;
import com.petShop.petShop.services.CashierService;
import com.petShop.petShop.services.ManagerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@RequestMapping("/")
@Controller
public class LoginController {

    @Autowired
    private CashierService cashierService;

    @Autowired
    private ManagerService managerService;

    // Show the login page
    @GetMapping("/log")
    public String showLoginPage() {
        return "products/login-pos"; //Corrected to match the location in templates
    }

    // Handle the login form submission
    @PostMapping("/log")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam("role") String role,
                        Model model, HttpSession session) {

        if (role.equals("CASHIER")) {
            // Authenticate as cashier
            Cashier cashier = cashierService.authenticateCashier(username, password);
            if (cashier != null) {
                // Set session attributes for cashier
                session.setAttribute("username", username);
                session.setAttribute("role", "CASHIER");
                return "redirect:/products/calculate-c"; // Redirect to homepage/dashboard
            }
        } else if (role.equals("MANAGER")) {
            // Authenticate as manager
            Manager manager = managerService.authenticateManager(username, password);
            if (manager != null) {
                // Set session attributes for manager
                session.setAttribute("username", username);
                session.setAttribute("role", "MANAGER");
                return "redirect:/products/calculate"; // Redirect to homepage/dashboard
            }
        }

        // If login fails, return to the login page with an error message
        model.addAttribute("error", "Invalid credentials or role");
        return "products/login-pos";
    }

    // Optional: Logout and clear session
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();  // Invalidate the session (log out)
        return "redirect:/log";  // Redirect to the login page
    }
}


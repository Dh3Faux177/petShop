package com.petShop.petShop.models;

import jakarta.validation.constraints.*;

public class CashierDto {

    private int id;

    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Username is required")
    @Size(min = 5, message = "Username should be at least 5 characters")
    private String username;

    @NotEmpty(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotNull @NotEmpty(message = "Password is required")
    @Size(min = 8, message = "Password should be at least 8 characters")
    private String password;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits")
    private String phone;

    //Add the role field
    //@NotNull(message = "Role is required")
    //private Role role; // Role is of type enum (CASHIER, MANAGER)

    // Getters and Setters
    public @NotEmpty(message = "Name is required") String getName() {
        return name;
    }

    public void setName(@NotEmpty(message = "Name is required") String name) {
        this.name = name;
    }

    public @NotEmpty(message = "Username is required") @Size(min = 5, message = "Username should be at least 5 characters") String getUsername() {
        return username;
    }

    public void setUsername(@NotEmpty(message = "Username is required") @Size(min = 5, message = "Username should be at least 5 characters") String username) {
        this.username = username;
    }

    public @NotEmpty(message = "Email is required") @Email(message = "Please provide a valid email address") String getEmail() {
        return email;
    }

    public void setEmail(@NotEmpty(message = "Email is required") @Email(message = "Please provide a valid email address") String email) {
        this.email = email;
    }

    public @NotEmpty(message = "Password is required") @Size(min = 8, message = "Password should be at least 8 characters") String getPassword() {
        return password;
    }

    public void setPassword(@NotEmpty(message = "Password is required") @Size(min = 8, message = "Password should be at least 8 characters") String password) {
        this.password = password;
    }

    public @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits") String getPhone() {
        return phone;
    }

    public void setPhone(@Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits") String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

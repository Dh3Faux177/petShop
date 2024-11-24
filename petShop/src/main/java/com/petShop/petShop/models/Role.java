package com.petShop.petShop.models;

public enum Role {
    CASHIER("Cashier"),
    MANAGER("Manager");

    private final String roleName;

    // Constructor to associate a string with the enum value
    Role(String roleName) {
        this.roleName = roleName;
    }

    // Getter method to retrieve the string representation of the role
    public String getRoleName() {
        return roleName;
    }

    // Optional: Method to retrieve Role enum from String (for database or API parsing)
    public static Role fromString(String roleName) {
        for (Role role : Role.values()) {
            if (role.roleName.equalsIgnoreCase(roleName)) {
                return role;
            }
        }
        throw new IllegalArgumentException("No constant with role name " + roleName);
    }
}

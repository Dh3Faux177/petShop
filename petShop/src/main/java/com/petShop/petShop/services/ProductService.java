package com.petShop.petShop.services;

import com.petShop.petShop.customException.ResourceNotFoundException;
import com.petShop.petShop.models.Product;
import com.petShop.petShop.services.ProductRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Fetch all products with sorting
    public List<Product> findAll() {
        return productRepository.findAll(Sort.by("id"));
    }

    // Search for products by name or category
    public List<Product> searchByNameOrCategory(String query) {
        return productRepository.findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(query, query);
    }

    public Product findProductById(Integer integer) {
            return productRepository.findById(integer)
                    .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + integer));
    }

    @Transactional
    public void updateProductQuantity(int productId, int quantityPurchased) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));

        System.out.println("Before update: " + product);

        if (product.getQuantity() < quantityPurchased) {
            throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
        }
        product.setQuantity(product.getQuantity() - quantityPurchased);
        productRepository.save(product);

        System.out.println("After update: " + product);
    }
}

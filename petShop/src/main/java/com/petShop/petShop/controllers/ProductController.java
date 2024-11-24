package com.petShop.petShop.controllers;

import com.petShop.petShop.models.Product;
import com.petShop.petShop.models.ProductDto;
import com.petShop.petShop.services.ProductRepository;
import com.petShop.petShop.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository repo;
    @Autowired
    private ProductService productService;

    private static final String UPLOAD_DIR = "C:/uploads/"; // Set your upload path here

    @GetMapping({"","/"})
    public String showProductList(Model model){
        List<Product> products = repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("products",products);
        return "products/index";
    }


    @GetMapping("/create")
    public String showCreateProduct(Model model){
        ProductDto productDto = new ProductDto();
        model.addAttribute("productDto",productDto);
        return "products/create-product";
    }

    // Handle Product Creation
    @PostMapping("/create")
    public String createProduct(@ModelAttribute ProductDto productDto,
                                RedirectAttributes redirectAttributes) {
        try {
            Product product = new Product();
            product.setName(productDto.getName());
            product.setBrand(productDto.getBrand());
            product.setCategory(productDto.getCategory());
            product.setPrice(productDto.getPrice());
            product.setQuantity(productDto.getQuantity());
            product.setDescription(productDto.getDescription());

            // Handle image upload
            MultipartFile imgFile = productDto.getImg();
            if (!imgFile.isEmpty()) {
                File uploadDir = new File(UPLOAD_DIR);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                String fileName = System.currentTimeMillis() + "_" + imgFile.getOriginalFilename();
                File destinationFile = new File(uploadDir, fileName);
                imgFile.transferTo(destinationFile);
                product.setImage(fileName);
            }

            repo.save(product);
            redirectAttributes.addFlashAttribute("success", "Product created successfully!");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Error uploading file: " + e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error creating product: " + e.getMessage());
        }
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String editProduct(Model model, @PathVariable int id) {
        try {
            Product product = repo.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
            model.addAttribute("product", product);

            // Create a ProductDto object and populate it with data
            ProductDto productDto = new ProductDto();
            productDto.setName(product.getName());
            productDto.setBrand(product.getBrand());
            productDto.setCategory(product.getCategory());
            productDto.setPrice(product.getPrice());
            productDto.setQuantity(product.getQuantity());
            productDto.setDescription(product.getDescription());
            productDto.setExistingImgPath(product.getImage()); // Populate existing image path

            model.addAttribute("productDto", productDto);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return "redirect:/products";
        }
        return "products/edit-product";
    }


    @PostMapping("/edit/{id}")
    public String updateProduct(Model model, @PathVariable int id, @Valid @ModelAttribute ProductDto productDto, BindingResult result) {
        if (result.hasErrors()) {
            Product product = repo.findById(id).get();
            model.addAttribute("product",product);
            return "products/edit-product";
        }

        // Retrieve existing product
        Product existingProduct = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + id));

        // Handle image upload (if new image is provided)
        /*String imagePath = existingProduct.getImage(); // Retain existing image if no new one is uploaded
        if (!img.isEmpty()) {
            try {
                String fileName = System.currentTimeMillis() + "_" + img.getOriginalFilename();
                File dest = new File(UPLOAD_DIR + fileName);
                img.transferTo(dest);
                imagePath = "/uploads/" + fileName;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

        // Update product fields with data from the form
        existingProduct.setName(productDto.getName());
        existingProduct.setBrand(productDto.getBrand());
        existingProduct.setCategory(productDto.getCategory());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setQuantity(productDto.getQuantity());
        existingProduct.setDescription(productDto.getDescription());
        //existingProduct.setImage(imagePath); // Update image path

        // If you're handling image files, make sure to add logic here to save and update image paths

        // Save the updated product
        repo.save(existingProduct);
        // Redirect to the product list or details page
        return "redirect:/products";
    }


    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable int id) {
        // Retrieve the product to confirm existence (optional)
        Product product = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + id));

        // Delete the product
        repo.delete(product);

        // Redirect to the product list
        return "redirect:/products";
    }

    //View product for manager
    @GetMapping("/view/{id}")
    public String viewProduct(@PathVariable int id, Model model) {
        Product product = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + id));
        model.addAttribute("product", product);
        return "products/view-product";
    }

    // Search products by name or category for manager
    @GetMapping("/search")
    public String searchProducts(@RequestParam("query") String query, Model model) {
        List<Product> products = productService.searchByNameOrCategory(query);
        model.addAttribute("products", products);
        return "products/index"; // Display results on the same template

    }

    // Display calculation page for manager
    @GetMapping("/calculate")
    public String showCalculationPage(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "products/purchase-product";
    }

    @PostMapping("/calculate")
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

    public void updateProductQuantity(int productId, int quantityPurchased) {
        Product product = repo.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));

        System.out.println("Product before update: " + product);

        if (product.getQuantity() < quantityPurchased) {
            throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
        }

        product.setQuantity(product.getQuantity() - quantityPurchased);
        repo.save(product);

        System.out.println("Product after update: " + product);
    }


    ///////////// FOR CASHIER///////////

    // List product for cashier
    @GetMapping({"/index-c"})
    public String showProductListCashier(Model model){
        List<Product> products = repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("products",products);
        return "products/index-cashier";
    }

    // Search products by name or category for cashier
    @GetMapping("/search-cashier")
    public String searchProductsCashier(@RequestParam("query") String query, Model model) {
        List<Product> products = productService.searchByNameOrCategory(query);
        model.addAttribute("products", products);
        return "products/index-cashier"; // Display results on the same template

    }

    // Display calculation page for cashier
    @GetMapping("/calculate-c")
    public String showCalculationPageCashier(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "products/purchase-product-cashier";
    }

    @PostMapping("/calculate-c")
    public String handlePurchaseCashier(@RequestParam Map<Integer, Integer> quantities, RedirectAttributes redirectAttributes) {
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

        return "redirect:/products/calculate-c";
    }

    // View product for cashier
    @GetMapping("/view-c/{id}")
    public String viewProductCashier(@PathVariable int id, Model model) {
        Product product = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + id));
        model.addAttribute("product", product);
        return "products/view-product-cashier";
    }

}
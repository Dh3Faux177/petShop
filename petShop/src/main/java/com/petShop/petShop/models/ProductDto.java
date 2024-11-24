package com.petShop.petShop.models;
import com.petShop.petShop.services.ProductRepository;
import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

public class ProductDto {

    @NotEmpty(message = "name is required")
    private String name;

    @NotEmpty(message = "the brand is required")
    private String brand;

    @NotEmpty(message = "the category is required")
    private String category;

    @Min(value = 0, message = "price cannot be negative")
    private double price;

    @Min(value = 0, message = "quantity cannot be negative")
    private int quantity;

    @Min(0)
    public double getPrice() {
        return price;
    }

    public void setPrice(@Min(0) double price) {
        this.price = price;
    }

    public @Size(min = 10, message = "the description should be atleast 10 character") @Size(max = 100, message = "the description should not exceed 200 characters") String getDescription() {
        return description;
    }

    public void setDescription(@Size(min = 10, message = "the description should be atleast 10 character") @Size(max = 100, message = "the description should not exceed 200 characters") String description) {
        this.description = description;
    }

    @Size(min = 10, message = "the description should be atleast 10 character")
    @Size(max = 100, message = "the description should not exceed 200 characters")
    private String description;

    private MultipartFile img; // Use MultipartFile to handle file upload

    private String existingImgPath;

    public @NotEmpty(message = "Name is Required") String getName()
    {
        return name;
    }

    public void setName(@NotEmpty(message = "Name is Required") String name)
    {
        this.name = name;
    }

    public @NotEmpty(message = "The Brand is Required") String getBrand()
    {
        return brand;
    }

    public void setBrand(@NotEmpty(message = "The Brand is Required") String brand)
    {
        this.brand = brand;
    }

    public @NotEmpty(message = "the category is Required") String getCategory()
    {
        return category;
    }

    public void setCategory(@NotEmpty(message = "the category is Required") String category)
    {
        this.category = category;
    }

    public @Min(value = 0, message = "quantity cannot be negative") int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(@Min(value = 0, message = "quantity cannot be negative") int quantity)
    {
        this.quantity = quantity;
    }

    public MultipartFile getImg()
    {
        return img;
    }

    public void setImg(MultipartFile img)
    {
        this.img = img;
    }

    public String getExistingImgPath()
    {
        return existingImgPath;
    }

    public void setExistingImgPath(String existingImgPath)
    {
        this.existingImgPath = existingImgPath;
    }
}
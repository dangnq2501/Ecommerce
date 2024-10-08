package com.example.ecommerce_backend.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class ProductRequest {
    UUID id;
    @Size(min=4, message = "Name_invalid")
    String name;
    @Size(min=4, message = "Category_invalid")
    String category;
    String description;
    String imageFile;
    double price;
    int stockQuantity;
}
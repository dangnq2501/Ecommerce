package com.example.ecommerce_backend.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class ProductResponse {
    UUID id;
    String name;
    String category;
    String description;
    String imageFile;
    double price;
    int stockQuantity;
}

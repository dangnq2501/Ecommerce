package com.example.ecommerce_backend.dto.response;

import com.example.ecommerce_backend.models.Product;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class CartItemResponse {
    UUID id;
    Product product;
    int quantity;
    double price;
}

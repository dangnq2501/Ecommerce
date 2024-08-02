package com.example.ecommerce_backend.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity(name="product")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    @Id
    @Column(name="product_id")
    String id;

    @Column(name="name")
    String name;

    @Column(name="decription")
    String description;

    @Column(name="category")
    String category;

    @Column(name="price")
    double price;

    @Column(name="imageFile")
    String imageFile;
}

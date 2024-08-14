package com.example.ecommerce_backend.models;
import org.hibernate.annotations.JdbcTypeCode;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Entity(name="product")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    @Id
    @Column(name="product_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

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

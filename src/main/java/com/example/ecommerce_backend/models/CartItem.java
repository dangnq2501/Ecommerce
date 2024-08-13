package com.example.ecommerce_backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Entity(name="cart_item")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItem {
    @Id
    @Column(name="cart_item_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name="price")
    double price;
    @Column(name="quantity")
    int quantity;

    @OneToOne
    Product product;
}

package com.example.ecommerce_backend.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity(name="cart")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cart {
    @Id
    @Column(name="cart_id")
    String cart_id;

    @Column(name="user_id")
    String user_id;

    @Column(name="total_price")
    double total_price;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> items = new ArrayList<>();

}

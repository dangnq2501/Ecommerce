package com.example.ecommerce_backend.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity(name="\"order\"")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Order {
    @Id
    @Column(name="\"order_id\"")
    String id;

    @Column(name="\"quantity\"")
    int quantity;

    @Column(name="\"paid\"")
    boolean paid;

    @OneToOne
    @JoinColumn(name = "\"product_id\"")
    Product product;

    @ManyToOne
    @JoinColumn(name = "\"user_id\"")
    User user;



}


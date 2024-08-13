package com.example.ecommerce_backend.models;
import org.hibernate.annotations.JdbcTypeCode;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Entity(name="\"order\"")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Order {
    @Id
    @Column(name="\"order_id\"")
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

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


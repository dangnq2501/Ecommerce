package com.example.ecommerce_backend.repository;

import com.example.ecommerce_backend.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
}

package com.example.ecommerce_backend.controller;

import com.example.ecommerce_backend.dto.response.ApiResponse;
import com.example.ecommerce_backend.dto.response.CartResponse;
import com.example.ecommerce_backend.service.CartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping
@RequiredArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE, makeFinal = true)
public class CartController {
    CartService cartService;

    @PostMapping("/user/{userId}/cart/add/{productId}")
    ApiResponse<CartResponse> addCartItem(@PathVariable("userId") UUID userId,
                                          @PathVariable("productId") UUID productID){
        CartResponse cartResponse = cartService.addCartItem(userId, productID);
        ApiResponse<CartResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(cartResponse);
        return apiResponse;
    }

    @GetMapping("/user/{userId}/cart")
    ApiResponse<CartResponse> getCart(@PathVariable("userId") UUID userId){
        CartResponse cartResponse = cartService.getCart(userId);
        ApiResponse<CartResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(cartResponse);
        return apiResponse;
    }
}

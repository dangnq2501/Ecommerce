package com.example.ecommerce_backend.service;

import com.example.ecommerce_backend.dto.response.CartResponse;
import com.example.ecommerce_backend.exception.AppException;
import com.example.ecommerce_backend.mapper.CartItemMapper;
import com.example.ecommerce_backend.mapper.CartMapper;
import com.example.ecommerce_backend.mapper.ProductMapper;
import com.example.ecommerce_backend.models.Cart;
import com.example.ecommerce_backend.models.CartItem;
import com.example.ecommerce_backend.models.Product;
import com.example.ecommerce_backend.models.User;
import com.example.ecommerce_backend.repository.CartItemRepository;
import com.example.ecommerce_backend.repository.CartRepository;
import com.example.ecommerce_backend.repository.ProductRepository;
import com.example.ecommerce_backend.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE, makeFinal = true)
public class CartService {
    CartMapper cartMapper;
    CartItemMapper cartItemMapper;
    ProductMapper productMapper;
    CartRepository cartRepository;
    CartItemRepository cartItemRepository;

    ProductRepository productRepository;
    UserRepository userRepository;

    public CartResponse getCart(UUID userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException("User not existed"));
        System.out.print("Username: ");
        System.out.println(user.getUsername());
        Cart cart = cartRepository.findById(user.getCart_id()) .orElseThrow(() -> new AppException("User not existed"));
        System.out.print("Cart ID: ");
        System.out.println(cart.getCart_id());
        CartResponse cartResponse = cartMapper.toCartResponse(cart);
        System.out.print("Cart ID: ");
        System.out.println(cartResponse.getCart_id());
        return cartResponse;
    }
    public CartResponse addCartItem(UUID userId, UUID productId){
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException("User not existed"));
        Cart cart = cartRepository.findById(user.getCart_id()) .orElseThrow(() -> new AppException("User not existed"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new AppException("Product not existed"));
        List<CartItem> cartItems = cart.getCartItems();

        for(int i = 0; i < cartItems.size(); i ++){
            CartItem curItem = cartItems.get(i);
            if(curItem.getProduct_id().equals(product.getId())){
                curItem.setQuantity(curItem.getQuantity()+1);
                curItem.setPrice(curItem.getPrice()+product.getPrice());
                cartItemRepository.save(curItem);
                cart.setTotal_price(cart.getTotal_price()+product.getPrice());
                cart.setCartItems(cartItems);

                return cartMapper.toCartResponse(cartRepository.save(cart));
            }
        }
        System.out.println("Finding sucessfully");
        CartItem newCartItem = new CartItem();
        System.out.println(cart.getCart_id());
        newCartItem.setProduct_id(product.getId());
        newCartItem.setPrice(product.getPrice());
        newCartItem.setQuantity(1);
        newCartItem.setCart_id(cart.getCart_id());
        System.out.println("Set sucessfully");
        cartItemRepository.save(newCartItem);
        System.out.println("Save sucessfully");
        cartItems.add(newCartItem);
        cart.setTotal_price(cart.getTotal_price()+product.getPrice());
        cart.setCartItems(cartItems);

        return cartMapper.toCartResponse(cartRepository.save(cart));
    }

    public CartResponse deleteCartItem(UUID userId, UUID productId){
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException("User not existed"));
        Cart cart = cartRepository.findById(user.getCart_id()) .orElseThrow(() -> new AppException("User not existed"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new AppException("Product not existed"));
        List<CartItem> cartItems = cart.getCartItems();
        for(int i = 0; i < cartItems.size(); i ++){
            CartItem curItem = cartItems.get(i);
            if(curItem.getProduct_id().equals(product.getId())){
                if(curItem.getQuantity() == 1){
                    cartItems.remove(i);
                    break;
                }
                curItem.setQuantity(curItem.getQuantity()-1);
                curItem.setPrice(curItem.getPrice()-product.getPrice());
                cart.setTotal_price(cart.getTotal_price()-product.getPrice());
                cart.setCartItems(cartItems);
                return cartMapper.toCartResponse(cartRepository.save(cart));
            }
        }
        cart.setCartItems(cartItems);
        return cartMapper.toCartResponse(cartRepository.save(cart));
    }

    public CartResponse deleteAllCartItem(UUID userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException("User not existed"));
        Cart cart = cartRepository.findById(user.getCart_id()) .orElseThrow(() -> new AppException("User not existed"));
        List<CartItem> cartItems = cart.getCartItems();
        cartItems.clear();
        cart.setCartItems(cartItems);
        return cartMapper.toCartResponse(cartRepository.save(cart));
    }
}

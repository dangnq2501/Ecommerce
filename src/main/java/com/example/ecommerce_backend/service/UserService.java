package com.example.ecommerce_backend.service;

import com.example.ecommerce_backend.dto.request.UserCreationRequest;
import com.example.ecommerce_backend.dto.request.UserUpdateRequest;
import com.example.ecommerce_backend.dto.response.UserResponse;
import com.example.ecommerce_backend.exception.AppException;
import com.example.ecommerce_backend.mapper.UserMapper;
import com.example.ecommerce_backend.models.Cart;
import com.example.ecommerce_backend.models.CartItem;
import com.example.ecommerce_backend.models.Role;
import com.example.ecommerce_backend.models.User;
import com.example.ecommerce_backend.repository.CartRepository;
import com.example.ecommerce_backend.repository.RoleRepository;
import com.example.ecommerce_backend.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE, makeFinal = true)
public class UserService implements IUserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    CartRepository cartRepository;
    @Override
    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException("User already existed");
        }
        User user = userMapper.toUser(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        List<Role> roles = roleRepository.findAllById(request.getRoles());

        user.setRoles(new HashSet<>(roles));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Cart cart = new Cart();
        List<CartItem> cartItems = new ArrayList<>();
        cart.setCartItems(cartItems);
        cart.setTotal_price(0);
        cartRepository.save(cart);
        for(Role r: roles){
            System.out.println("Role: " + r.getName());
        }

        user.setCart_id(cart.getCart_id());
        System.out.print("Cart ID: ");
        System.out.println(user.getCart_id());

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse updateUser(UUID userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException("User not existed"));

        return updateUser(request, user);
    }
    @Override
    public UserResponse updateUser(UserUpdateRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(() -> new AppException("User not existed"));

        return updateUser(request, user);
    }

    private UserResponse updateUser(UserUpdateRequest request, User user) {
        if (request.getRoles() != null) {
            List<Role> roles = roleRepository.findAllById(request.getRoles());
            user.setRoles(new HashSet<>(roles));
        }

        if (request.getPassword() != null) {
            request.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        userMapper.updateUser(user, request);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException("User not existed"));
        return userMapper.toUserResponse(user);
    }

    @Override
    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getMyInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new AppException("User not existed"));

        return userMapper.toUserResponse(user);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

}

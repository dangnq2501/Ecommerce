package com.example.ecommerce_backend.service;


import com.example.ecommerce_backend.dto.request.UserCreationRequest;
import com.example.ecommerce_backend.dto.request.UserUpdateRequest;
import com.example.ecommerce_backend.dto.response.UserResponse;

import java.util.List;

public interface IUserService {

    UserResponse createUser(UserCreationRequest userRequest);

    UserResponse updateUser(String id, UserUpdateRequest userRequest);

    UserResponse updateUser(UserUpdateRequest userRequest);

    UserResponse getUserById(String id);

    UserResponse getMyInfo();

    List<UserResponse> getUsers();

    void deleteUser(String id);
}
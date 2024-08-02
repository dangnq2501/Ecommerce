package com.example.ecommerce_backend.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)
public class UserUpdateRequest {

    @Size(min=8, message="Password_invalid")
    String password;
    String email;
    String phone;
    boolean enable;
    Set<String> roles;
}

package com.example.ecommerce_backend.models;

import org.hibernate.annotations.JdbcTypeCode;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;
import java.util.UUID;

@Entity(name="user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)
public class User {
    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name="username")
    String username;

    @Column(name="password")
    String password;

    @Column(name="email")
    String email;

    @Column(name="phone")
    String phone;

    @Column(name="enabled")
    boolean isEnable;

    @Column(name="cart_id")
    UUID cart_id;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.DETACH,
            CascadeType.REFRESH
    })
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_name")
    )
    Set<Role> roles;
}
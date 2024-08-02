package com.example.ecommerce_backend.repository;

import com.example.ecommerce_backend.models.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, String> {
}

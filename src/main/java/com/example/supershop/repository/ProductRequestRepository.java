package com.example.supershop.repository;

import com.example.supershop.model.ProductRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRequestRepository extends JpaRepository<ProductRequest, Long> {
    Optional<ProductRequest> findByAndRequestIdAndIsActiveTrue(long id);
}

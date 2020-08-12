package com.example.supershop.repository;

import com.example.supershop.model.ProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;
@Repository
public interface ProductRequestRepository extends JpaRepository<ProductRequest, Long> {
    Optional<ProductRequest> findByAndRequestIdAndIsActiveTrue(long id);
    Page<ProductRequest> findAllByIsActiveTrueAndCreatedAt(Date date, Pageable pageable);
    Page<ProductRequest> findAllByIsActiveTrueAndCreatedAtOrderByCreatedAt(Date date, Pageable pageable);
    Page<ProductRequest> findAllByIsActiveTrueAndRequestShopShopId(long shopId,Pageable pageable);
    Page<ProductRequest> findAllByIsActiveTrueAndWareHouseWarehouseId(long warehouseId,Pageable pageable);

}

package com.example.supershop.repository;

import com.example.supershop.model.WareHouseStock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WareHouseStockRepository extends JpaRepository<WareHouseStock,Long> {
    Page<?> findAllByWareHouseWarehouseIdAndIsActiveTrue(long warehouseId, Pageable pageable);
    Optional<WareHouseStock> findByStockIdAndIsActiveTrue(long stockId);
    Optional<WareHouseStock> findByWareHouseWarehouseIdAndProductProductIdAndIsActiveTrue(long warehouseId,long productId);
}

package com.example.supershop.repository;

import com.example.supershop.model.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RepositoryRestResource
public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByStockIdAndIsActiveTrue(long id);

    Page<?> findAllByShopShopId(long id, Pageable pageable);

    Optional<Stock> findByShop_ShopIdAndProduct_ProductId(long shopId, long productId);

    Optional<Stock> findByStockIdAndIsActiveTrueAndShop_ShopIdAndProduct_ProductId(long stockId, long shopId, long productId);

    List<Stock> findAllByIsActiveTrueAndQuantityLessThan(int quantity);
}

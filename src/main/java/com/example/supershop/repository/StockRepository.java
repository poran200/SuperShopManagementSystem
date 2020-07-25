package com.example.supershop.repository;

import com.example.supershop.model.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RepositoryRestResource
public interface StockRepository extends JpaRepository<Stock, Long> {

    Page<?> findAllByShopShopId( long id,Pageable pageable);
    Optional<Stock> findByShop_ShopIdAndProduct_ProductId(long shopId,long productId);
}

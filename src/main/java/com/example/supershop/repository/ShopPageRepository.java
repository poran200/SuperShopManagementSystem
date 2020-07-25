package com.example.supershop.repository;

import com.example.supershop.model.Shop;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopPageRepository extends PagingAndSortingRepository<Shop, Long> {
}

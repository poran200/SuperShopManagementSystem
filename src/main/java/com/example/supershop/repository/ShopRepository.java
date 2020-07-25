package com.example.supershop.repository;

import com.example.supershop.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RepositoryRestResource
public interface ShopRepository  extends JpaRepository<Shop,Long> {
       Optional<Shop> findByShopName(String shopName);

       @Query(value =
               "select s from Shop s  join  Address a on  a.id = s.shopAddress.id where  a.city = :cityName")
       Optional<Shop> findShopByCity(@Param("cityName") String cityName);


}

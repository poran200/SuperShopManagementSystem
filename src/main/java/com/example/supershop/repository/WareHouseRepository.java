package com.example.supershop.repository;

import com.example.supershop.model.WareHouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WareHouseRepository  extends JpaRepository<WareHouse,Long> {

    Optional<WareHouse> findByWarehouseIdAndIsActiveTrue(long wareHouseId);
    List<WareHouse> findAllByIsActiveTrue();
    Page<?> findAllByIsActiveTrue(Pageable pageable);
    Optional<WareHouse> findByName(String name);
}

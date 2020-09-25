package com.example.supershop.repository;

import com.example.supershop.model.ParchedInvoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParchedInvoiceRepository extends JpaRepository<ParchedInvoice, Long> {
    Page<ParchedInvoice> findAllByIsActiveTrueAndProviderId(long providerId, Pageable pageable);

    Optional<ParchedInvoice> findByIdAndIsActiveTrue(long id);

    Page<ParchedInvoice> findAllByIsActiveTrue(Pageable pageable);

    Page<ParchedInvoice> findAllByWareHouseWarehouseIdAndIsActiveTrue(long warehouseId, Pageable pageable);
}

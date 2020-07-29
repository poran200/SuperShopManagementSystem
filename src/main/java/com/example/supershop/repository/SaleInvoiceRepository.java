package com.example.supershop.repository;

import com.example.supershop.model.SalesInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RepositoryRestResource
public interface SaleInvoiceRepository extends JpaRepository<SalesInvoice, Integer> {

   List<SalesInvoice> findAllByShop_ShopId(long shopId);
   Optional<SalesInvoice> findBySaleInvoiceIdAndIsActiveTrue(int invoiceId );
}

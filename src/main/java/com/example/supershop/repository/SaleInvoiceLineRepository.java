package com.example.supershop.repository;

import com.example.supershop.model.ParchedI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource
public interface SaleInvoiceLineRepository extends JpaRepository<ParchedI, Long> {
}

package com.example.supershop.repository;

import com.example.supershop.model.Provider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RepositoryRestResource
public interface ProviderRepository extends JpaRepository<Provider, Long> {

    Page findByIdAndIsActiveTrue(long id, Pageable pageable);

     Optional<Provider> findByIdAndIsActiveTrue(Long providerId);
}

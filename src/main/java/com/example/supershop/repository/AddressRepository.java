package com.example.supershop.repository;

import com.example.supershop.model.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;
@RepositoryRestResource(path = "address")
public interface AddressRepository extends JpaRepository<Address,Long> {
       Optional<Address>   findByIdAndIsActiveTrue(long id);
       Page<Address>  findAllByIsActiveTrue(Pageable pageable);
}

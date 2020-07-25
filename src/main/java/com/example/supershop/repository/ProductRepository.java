package com.example.supershop.repository;

import com.example.supershop.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RepositoryRestResource
public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findByProductName(String productName);
    Product findByProductIdAndProductName(long id,String name);
    Optional<Product> findByProductIdAndIsActiveTrue(long productId);
   Page<?> findAllByCategoryId(Pageable pageable,long categoryId);


}

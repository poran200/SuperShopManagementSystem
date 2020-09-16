package com.example.supershop.repository;

import com.example.supershop.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RepositoryRestResource
public interface CategoryRepository extends JpaRepository<Category,Long> {
        Optional<Category> findByIdAndIsActiveTrue(long id);

        Page<Category> getCategoriesByCategoryNameContains(String name, Pageable pageable);
}

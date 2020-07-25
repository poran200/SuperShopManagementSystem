package com.example.supershop.repository;

import com.example.supershop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource
public interface CategoryRepository extends JpaRepository<Category,Long> {

}

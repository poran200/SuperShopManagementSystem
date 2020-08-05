package com.example.supershop.repository;

import com.example.supershop.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RepositoryRestResource
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
      Optional<Employee> findByIdAndIsActiveTrue(long id);
      Page<?> findAllByIsActiveTrue(Pageable pageable);
}

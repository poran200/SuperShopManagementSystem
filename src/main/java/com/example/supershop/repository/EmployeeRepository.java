package com.example.supershop.repository;

import com.example.supershop.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
      Optional<Employee> findByIdAndIsActiveTrue(long id);

      Page<Employee> findAllByIsActiveTrue(Pageable pageable);

      Page<?> findAllByIsActiveTrueAndPosition(String position, Pageable pageable);
}

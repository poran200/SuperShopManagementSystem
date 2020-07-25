package com.example.supershop.repository;

import com.example.supershop.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
}

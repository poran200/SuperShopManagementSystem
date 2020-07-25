package com.example.supershop.repository;

import com.example.supershop.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@RepositoryRestResource
public interface RoleRepository extends JpaRepository<Role,Long> {

}

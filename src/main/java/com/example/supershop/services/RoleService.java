package com.example.supershop.services;

import com.example.supershop.exception.EntityAlreadyExistException;
import com.example.supershop.exception.EntityNotFoundException;
import com.example.supershop.model.Role;
import com.example.supershop.repository.RoleRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role createRole(Role role) throws EntityAlreadyExistException {
        Optional<Role> roleOptional = roleRepository.findById(role.getId());
        if (roleOptional.isPresent()) throw new EntityAlreadyExistException(role);
         return roleRepository.save(role);
    }

    public Optional<Role> findById(long roleId) throws EntityNotFoundException {
        Optional<Role> role = roleRepository.findById(roleId);
        if (role.isPresent()){
            return role;
        }
        throw  new EntityNotFoundException(role);

    }

    public  void deleteRole(long roleId) throws EntityNotFoundException {
        if (findById(roleId).isPresent()){
            roleRepository.deleteById(roleId);
        }else {
               throw new EntityNotFoundException(roleId+" Not found");
        }
    }
}

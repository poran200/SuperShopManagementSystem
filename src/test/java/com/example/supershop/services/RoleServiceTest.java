package com.example.supershop.services;

import com.example.supershop.exception.EntityNotFoundException;
import com.example.supershop.model.Role;
import com.example.supershop.repository.RoleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.configuration.IMockitoConfiguration;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {
     @Mock
     RoleRepository roleRepository;
     @InjectMocks
     RoleService roleService;


    @Test
    void createRoleTest() throws Exception {
        Role role = new Role(1, "admin");
        when(roleRepository.save(role)).thenReturn(role);
        assertEquals(roleService.createRole(role),role);
    }

    @Test
    @DisplayName("find by id test")
    void findByIdTest() throws EntityNotFoundException {

        when(roleRepository.findById(1L))
                .thenReturn(Optional.of(new Role(1,"admin")));
        assertEquals(1,roleService.findById(1).get().getId());

    }


    @DisplayName("find by id test and throw exception")
    @Test
    void findByIdThrowExceptionTest()   {
        assertThrows(EntityNotFoundException.class,()-> roleService.findById(2L));
    }

    @Test
    void deleteRoleTest() throws EntityNotFoundException {
//        setup
    when(roleRepository.findById(1L))
                .thenReturn(Optional.of(new Role(1,"admin")));

//    arrange
        roleService.deleteRole(1);

        verify(roleRepository, atLeastOnce()).deleteById(1L);
    }

}
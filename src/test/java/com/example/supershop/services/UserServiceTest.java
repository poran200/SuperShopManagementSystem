package com.example.supershop.services;

import com.example.supershop.enam.Gender;
import com.example.supershop.exception.EntityAlreadyExistException;
import com.example.supershop.model.*;
import com.example.supershop.repository.AddressRepository;
import com.example.supershop.repository.EmployeeRepository;
import com.example.supershop.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

//@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UserServiceTest {
   @Autowired
   UserRepository userRepository;
   @Autowired
   EmployeeRepository employeeRepository;
   @Autowired
   AddressRepository addressRepository;

   @Autowired
   UserService userService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createUser() throws EntityAlreadyExistException {
        Set<Role>  roleList = Set.of(new Role(1," admin"));
        Name name = new Name("poran","chowdury");
        Address address = new Address( "dhaka", "nikonjo");
        List<String> list = new ArrayList<>();
       list.add("01757414897");
        Employee employee  = new Employee(2001, name, new Date(), "p@email.com",list,new Date(),
                Gender.MALE, "accountant", address, null, null);
        User  user = new User(101L,"hello","123",roleList,employee);
//        when(addressRepository.save(Mockito.any(Address.class))).thenReturn(address);
//        when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(employee);
//        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        User createUser = userService.createUser(user);
        assertEquals(user.getUserId(),createUser.getUserId());
        assertEquals(user.getPassword(),createUser.getPassword());
        assertEquals(user. getRoles(),createUser. getRoles());
        assertEquals(user.  getEmployee(),createUser. getEmployee());
    }
}
package com.example.supershop.services;

import com.example.supershop.enam.Gender;
import com.example.supershop.model.Address;
import com.example.supershop.model.Employee;
import com.example.supershop.model.Name;
import com.example.supershop.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
@SpringBootTest
class EmployeeDtoServiceTest {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeRepository employeeRepository;

    Name name = new Name("poran","chowdury");
    Name name2 = new Name("iqbal","hasan");
    Address address = new Address( "dhaka", "nikonjo");
    List<String> list = List.of("01757414897");

    Employee employee = new Employee(2001, name, new Date(), "p@email.com", list, new Date(),
            Gender.MALE, "accountant", address, null, null);
    Employee employee2 = new Employee(2002, name2, new Date(), "i@email.com", list, new Date(),
            Gender.MALE, "manager", address, null, null);
    @Test
    void update() {
        employeeRepository.save(employee2);
        //        employeeService.update(2002,employee2);
    }
}
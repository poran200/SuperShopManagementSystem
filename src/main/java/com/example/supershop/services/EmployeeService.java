package com.example.supershop.services;

import com.example.supershop.exception.EntityNotFoundException;
import com.example.supershop.model.Employee;
import com.example.supershop.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
   private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee  update(long id, Employee employee){
        var optional = employeeRepository.findById(id);
        if (optional.isPresent()){
             optional.get().setManager(employee.getManager());
          return   employeeRepository.save(employee);
        }
        throw  new EntityNotFoundException("Not found Id: "+id);
    }
}

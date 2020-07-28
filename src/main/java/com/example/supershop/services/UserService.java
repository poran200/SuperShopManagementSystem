package com.example.supershop.services;

import com.example.supershop.exception.EntityAlreadyExistException;
import com.example.supershop.exception.EntityNotFoundException;
import com.example.supershop.model.Address;
import com.example.supershop.model.Employee;
import com.example.supershop.model.User;
import com.example.supershop.repository.AddressRepository;
import com.example.supershop.repository.EmployeeRepository;
import com.example.supershop.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService {
    final UserRepository userRepository;
    final EmployeeRepository employeeRepository;
    final AddressRepository addressRepository;

    public UserService(UserRepository userRepository, EmployeeRepository employeeRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
        this.addressRepository = addressRepository;
    }
    @Transactional
    public User createUser(User user) throws EntityAlreadyExistException {
        Employee employee = user.getEmployee();
        Optional<User> optionalUser = userRepository.findById(user.getUserId());
        if (optionalUser.isPresent()){
            throw new EntityAlreadyExistException(user);
        }
        Address save = addressRepository.save(employee.getAddress());
        System.out.println("save = " + save);
        employeeRepository.save(employee);
        return userRepository.save(user);
    }
    public User gerById(long userId) throws EntityNotFoundException{
        Optional<User> optionalUser = userRepository.findById(userId);
         if (optionalUser.isPresent()){
              return optionalUser.get();
         }else
             throw  new EntityNotFoundException("User Not Found : "+userId);
    }



}

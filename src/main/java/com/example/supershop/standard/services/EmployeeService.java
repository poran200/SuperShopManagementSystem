package com.example.supershop.standard.services;

import com.example.supershop.dto.request.EmployeeDto;
import com.example.supershop.dto.respose.Response;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {
    Response create(EmployeeDto dto);
    Response findById(long id);
    Response findAllByPage(Pageable pageable);
    Response update(long id , EmployeeDto dto);
    Response delete(long id);

    Response findEmployeeByPosition(String position, Pageable pageable);
}

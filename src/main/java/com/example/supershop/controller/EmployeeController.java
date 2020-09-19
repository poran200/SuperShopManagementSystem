package com.example.supershop.controller;

import com.example.supershop.anotation.APiController;
import com.example.supershop.anotation.DataValidation;
import com.example.supershop.dto.request.EmployeeDto;
import com.example.supershop.standard.services.EmployeeService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.supershop.util.UrlConstrains.EmployeeManagement;

@APiController
@RequestMapping(EmployeeManagement.ROOT)
public class EmployeeController {
    private final EmployeeService employeeService;


    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @PostMapping(EmployeeManagement.CREATE)
    @DataValidation
    public ResponseEntity<Object> create(@Valid @RequestBody EmployeeDto employeeDto , BindingResult bindingResult) {
        var response = employeeService.create(employeeDto);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }


    @GetMapping(EmployeeManagement.FIND_BY_ID)
    public ResponseEntity<Object> getById(@PathVariable long id) {
        var response = employeeService.findById(id);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }


    @GetMapping(EmployeeManagement.ALL_BY_PAGE)
    public ResponseEntity<Object> getByPage(@RequestParam(required = false) Pageable pageable) {
        var response = employeeService.findAllByPage(pageable);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }

    @PutMapping(EmployeeManagement.UPDATE)
    @DataValidation
    public ResponseEntity<Object> update(@PathVariable long id, @RequestBody EmployeeDto dto, BindingResult bindingResult) {
        var response = employeeService.update(id, dto);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }

    @DeleteMapping(EmployeeManagement.DELETE)
    public ResponseEntity<Object> delete(@PathVariable long id){
        var response = employeeService.delete(id);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }
}

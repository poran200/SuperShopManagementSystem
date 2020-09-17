package com.example.supershop.controller;

import com.example.supershop.anotation.APiController;
import com.example.supershop.anotation.DataValidation;
import com.example.supershop.dto.request.EmployeeDto;
import com.example.supershop.dto.respose.EmployeeResponseDto;
import com.example.supershop.standard.services.EmployeeService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.supershop.util.UrlConstrains.EmployeeManagement;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
        if (response.getContent() != null) {
            response.setContent(addLinkWithRel(response.getContent()));
        }

        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }


    @GetMapping(EmployeeManagement.FIND_BY_ID)
    public ResponseEntity<Object> getById(@PathVariable long id) {
        var response = employeeService.findById(id);
        if (response.getContent() != null) {
            response.setContent(addLinkWithRel(response.getContent()));
        }
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }

    private EmployeeResponseDto addLinkWithRel(Object response) {
        EmployeeResponseDto responseDto = (EmployeeResponseDto) response;
        responseDto.add(linkTo(methodOn(EmployeeController.class).getById(responseDto.getId())).withSelfRel());
        return responseDto;
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

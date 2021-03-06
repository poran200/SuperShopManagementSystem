package com.example.supershop.standard.services.impl;

import com.example.supershop.controller.EmployeeController;
import com.example.supershop.dto.request.EmployeeDto;
import com.example.supershop.dto.respose.EmployeeResponseDto;
import com.example.supershop.dto.respose.Response;
import com.example.supershop.model.Employee;
import com.example.supershop.repository.AddressRepository;
import com.example.supershop.repository.EmployeeRepository;
import com.example.supershop.standard.services.EmployeeService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.example.supershop.util.ResponseBuilder.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service("EmployeeService")
@Log4j2
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    private final ModelMapper modelMapper;
    private final EmployeeRepository employeeRepository;
    private final AddressRepository addressRepository;

    public EmployeeServiceImpl(ModelMapper modelMapper, EmployeeRepository employeeRepository, AddressRepository addressRepository) {
        this.modelMapper = modelMapper;
        this.employeeRepository = employeeRepository;
        this.addressRepository = addressRepository;
    }

    private void mapping() {
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.createTypeMap(EmployeeDto.class, Employee.class)
                .addMapping(EmployeeDto::getName, Employee::setName)
                .addMapping(EmployeeDto::getAddress, Employee::setAddress)
                .addMapping(EmployeeDto::getManager, Employee::setManager);
    }

    @Override
    public Response create(EmployeeDto dto) {
        log.info("employee dto: " + dto);
        try {
            var employee = modelMapper.map(dto, Employee.class);
            log.info("employee object after convert: " + employee);
            var address = addressRepository.save(employee.getAddress());
            employee.setAddress(address);
            var saveEmployee = employeeRepository.save(employee);
            return getSuccessResponse(HttpStatus.CREATED, "employee created", saveEmployee);
        } catch (Exception e) {
            log.error(e.getMessage());
            return getInternalServerError();
        }
    }

    @Override
    public Response findById(long id) {
        var optionalEmployee = employeeRepository.findByIdAndIsActiveTrue(id);
        if (optionalEmployee.isPresent()) {
            var responseDto = addLinkToEmp(optionalEmployee.get());
            return getSuccessResponse(HttpStatus.OK, "employee found", responseDto);

        } else {
            return getFailureResponse(HttpStatus.NOT_FOUND, "employee not found Id: " + id);
        }
//        return optionalEmployee .map(employee ->
//               getSuccessResponse(HttpStatus.OK,"employee found",
//                       modelMapper.map(employee, EmployeeResponseDto.class)))
//               .orElse(getFailureResponse(HttpStatus.NOT_FOUND,"employee not found"));
    }

    @Override
    public Response findAllByPage(Pageable pageable) {
        Page<?> page = employeeRepository.findAllByIsActiveTrue(pageable);
        Page<EmployeeResponseDto> emPage = new PageImpl<>(getEmployeeResponseDtoWithLink(page));
        if (page.getContent().isEmpty()) return getFailureResponse(HttpStatus.OK, "No content found");
        return getSuccessResponsePage(HttpStatus.OK, "Employee page", emPage);
    }


    @Override
    public Response update(long id, EmployeeDto dto) {
        var employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isPresent()) {
            var employee = employeeOptional.get();
            var editEmployee = modelMapper.map(dto, Employee.class);
            var address = editEmployee.getAddress();

            editEmployee.setId(employee.getId());
            address.setId(employee.getAddress().getId());
            editEmployee.setAddress(address);
            var updateEmployee = employeeRepository.save(editEmployee);
            return getSuccessResponse(HttpStatus.OK, "employee updated ",
                    addLinkToEmp(updateEmployee));
        }
        return getFailureResponse(HttpStatus.NOT_FOUND, "employee not found Id: " + id);
    }

    @Override
    public Response delete(long id) {
        var optionalEmployee = employeeRepository.findByIdAndIsActiveTrue(id);
        if (optionalEmployee.isPresent()) {
            optionalEmployee.get().setIsActive(false);
            employeeRepository.save(optionalEmployee.get());
        }
        return getFailureResponse(HttpStatus.NOT_FOUND, "Employee not found Id: " + id);
    }

    @Override
    public Response findEmployeeByPosition(String position, Pageable pageable) {
        return null;
    }


    private List<EmployeeResponseDto> getEmployeeResponseDtoWithLink(Page<?> page) {
        List<EmployeeResponseDto> responseDtoList = new ArrayList<>();
        for (Object o : page.getContent()) {
            var employee = (Employee) o;
            responseDtoList.add(addLinkToEmp(employee));
        }
        return responseDtoList;
    }

    private EmployeeResponseDto addLinkToEmp(Employee employee) {
        var em = modelMapper.map(employee, EmployeeResponseDto.class);
        if (employee.getManager() != null) {
            em.add(linkTo(methodOn(EmployeeController.class)
                    .getById(employee.getManager().getId())).withRel("manager"));
        }
        em.add(linkTo(methodOn(EmployeeController.class)
                .getById(em.getId())).withSelfRel());
        log.info("add additional link done");
        return em;
    }
//    private EmployeeResponseDto addLinkToEmp( Employee employee) {
//        var em = modelMapper.map(employee, EmployeeResponseDto.class);
//        em.add(linkTo(methodOn(EmployeeController.class)
//                .getById(em.getId())).withSelfRel());
//        em.add(linkTo(methodOn(EmployeeController.class)
//                .getById(employee.getManager().getId())).withRel("manager"));
//        return em;
//    }

}
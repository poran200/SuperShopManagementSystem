package com.example.supershop.standard.services.impl;

import com.example.supershop.dto.request.EmployeeDto;
import com.example.supershop.dto.respose.Response;
import com.example.supershop.model.Employee;
import com.example.supershop.repository.AddressRepository;
import com.example.supershop.repository.EmployeeRepository;
import com.example.supershop.standard.services.EmployeeService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.supershop.util.ResponseBuilder.*;

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

    @Override
    public Response create(EmployeeDto dto) {
        log.info("employee dto: "+dto );
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.createTypeMap(EmployeeDto.class,Employee.class)
                   .addMapping(EmployeeDto::getName,Employee::setName)
                   .addMapping(EmployeeDto::getManager,Employee::setManager);
            try {
                var employee = modelMapper.map(dto, Employee.class);
                log.info("employee object after convert: "+employee);
                var address = addressRepository.save(employee.getAddress());
                employee.setAddress(address);
                var saveEmployee = employeeRepository.save(employee);
                return getSuccessResponse(HttpStatus.CREATED,"employee created",saveEmployee);
            } catch (Exception e){
            log.error(e.getMessage());
             return getInternalServerError();
            }



    }

    @Override
    public Response findById(long id) {
       return employeeRepository.findByIdAndIsActiveTrue(id).map(employee ->
               getSuccessResponse(HttpStatus.OK,"employee found",
                       modelMapper.map(employee,EmployeeDto.class)))
               .orElse(getFailureResponse(HttpStatus.NOT_FOUND,"employee not found"));

    }

    @Override
    public Response findAllByPage(Pageable pageable) {
        var page = employeeRepository.findAllByIsActiveTrue(pageable).map(o -> modelMapper.map(o, EmployeeDto.class));
        if (page.getContent().isEmpty()) return getFailureResponse(HttpStatus.OK,"No content found");
        return getSuccessResponsePage(HttpStatus.OK,"Employee page",page);
    }

    @Override
    public Response update(long id, EmployeeDto dto) {
        var employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isPresent()){
            var employee = employeeOptional.get();
          employee =  modelMapper.map(dto,Employee.class);
            var updateEmployee = employeeRepository.save(employee);
            return getSuccessResponse(HttpStatus.OK,"employee updated ",updateEmployee);
        }
        return getFailureResponse(HttpStatus.NOT_FOUND,"employee not found Id: "+id);
    }

    @Override
    public Response delete(long id) {
        var optionalEmployee = employeeRepository.findByIdAndIsActiveTrue(id);
        if (optionalEmployee.isPresent()){
            optionalEmployee.get().setIsActive(false);
            employeeRepository.save(optionalEmployee.get());
        }
        return getFailureResponse(HttpStatus.NOT_FOUND,"Employee not found Id: "+id);
    }
}

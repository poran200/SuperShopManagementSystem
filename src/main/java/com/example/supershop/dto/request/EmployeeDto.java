package com.example.supershop.dto.request;

import com.example.supershop.dto.AddressDto;
import com.example.supershop.enam.Gender;
import com.example.supershop.model.Employee;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
@Data
public class EmployeeDto {

    private long id;
    @NotNull
    @Valid
    private Name name;

    private Date dateOfBirth;
    @Email
    private String email;
    @Size(min = 1,max = 3,message = "1 phone number must be nedded")
     private List<@Valid @Size(min = 11, max = 11 ,message = "phone number must be 11 digit") String>
            phoneNumber = new ArrayList<>();
//    @JsonFormat(pattern = "mm-dd-yyy")
    private  Date joinDate;
    @NotNull
    private Gender gender;
    @NotEmpty(message = "Position not empty")
    private String position;
    @Valid
    @NotNull
    private AddressDto addressDto;
    private Employee manager;
    private Set<Employee> subordinates;
    @Data
    public static class Name{
        @NotEmpty(message = "fist name not empty")
        @Size(min = 3 ,max = 20, message = "first name between 3-20 character")
        private String firstName;
        @NotEmpty(message = "last name not empty")
        @Size(min = 3 ,max = 20, message = "first name between 3-20 character")
        private String lastName;
    }
}

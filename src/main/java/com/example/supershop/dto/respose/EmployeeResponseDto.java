package com.example.supershop.dto.respose;

import com.example.supershop.dto.AddressDto;
import com.example.supershop.dto.request.EmployeeDto;
import com.example.supershop.enam.Gender;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class EmployeeResponseDto extends RepresentationModel<EmployeeResponseDto> {
    private long id;

    private EmployeeDto.Name name;

    private Date dateOfBirth;
    private String email;
    private List<String>
            phoneNumber = new ArrayList<>();
    //    @JsonFormat(pattern = "mm-dd-yyy")
    private Date joinDate;
    private Gender gender;
    private String position;
    private AddressDto address;

    //    private Employee manager;
//    private Set<Employee> subordinates;
    @Data
    public static class Name {
        private String firstName;
        private String lastName;
    }
}

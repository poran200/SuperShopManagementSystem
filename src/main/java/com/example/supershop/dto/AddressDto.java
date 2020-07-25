package com.example.supershop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto  {

    private long id;
    @NotEmpty(message = "City is mandatory")
    private  String city;
    @NotEmpty(message = "Address details is mandatory")
    private String details;
}

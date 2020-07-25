package com.example.supershop.dto.request;

import com.example.supershop.dto.AddressDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
@Data
public class CreateProviderRequest {
    private  Long id;
    @NotEmpty(message = "provider name is not empty")
    @Size(min = 3, message = "provider name at last 3 character")
    private  String providerName;

    @NotEmpty(message = "provider type is not empty")
    @Size(min = 3, message = "provider type at last 3 character")
    private  String providerType;

    @NotNull(message = "phone number list is not empty")
    private List<@Size(min = 8,max = 11,message = "phone number between 8 to 11 ") String> phoneList;

    @NotEmpty(message = "email is mandatory")
    @Email(message = "email has contain . and @")
    private  String email;

    @NotEmpty(message = "description is not empty")
    private  String description;

    @NotEmpty(message = "address is mandatory")
    private @Valid AddressDto address;
}

package com.example.supershop.dto.respose;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Data
public class ProviderResponseDto extends RepresentationModel<ProviderResponseDto> {
    private  String providerName;
    private  String providerType;
    private List<String> phoneList;
    private  String email;
    private  String description;

}

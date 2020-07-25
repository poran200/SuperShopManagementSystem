package com.example.supershop.dto.respose;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

@EqualsAndHashCode(callSuper = false)
@Data
public class ProductResponseDto  extends RepresentationModel<ProductResponseDto> {
    private long id;
    private String productName;
    private String details;
    private Date expireDate;
    private double sellPrice;
    private  double vat ;
    @JsonIgnore
    private long categoryId;


}

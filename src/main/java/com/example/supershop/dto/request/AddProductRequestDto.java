package com.example.supershop.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddProductRequestDto {

    @NotNull(message = " Oops warehouseId mandatory ")
    private  long warehouseId;
    @NotNull(message = " Oops productId mandatory ")
    private long  productId;

}

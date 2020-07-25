package com.example.supershop.dto.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class ItemLineRequest {

    @NotNull(message = "productId  id  is mandatory")
    private long productId;
    @Min(value = 1,message = "quantity not less than 1")
    @NotNull
    private int quantity;

}

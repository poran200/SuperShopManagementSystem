package com.example.supershop.dto.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class CreateStockRequestDto {
     @NotNull(message = "shopId null not acceptable")
     private  long warehouseId;
     @NotNull(message = "productId null not acceptable")
     private  long productId;
     @NotNull(message = "product quantity null not acceptable")
     @Min(value = 1,message = "quantity at last 1 require")
     private  int quantity;
}

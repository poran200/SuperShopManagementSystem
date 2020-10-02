package com.example.supershop.dto.respose;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
public class LineItemResponse {
    private int parchedItemId;
    @JsonIgnoreProperties({"links", "expireDate", "details", "categoryId"})
    private ProductResponseDto product;
    private int parchedItemQuantity;
    private double totalItemLinePrice;

}

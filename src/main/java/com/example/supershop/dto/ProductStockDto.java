package com.example.supershop.dto;

import com.example.supershop.model.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = false)
@Data
@JsonPropertyOrder(value = {"product","price","quantity","categoryId","shopId"})
public class ProductStockDto extends RepresentationModel<EntityModel<ProductStockDto>> {
    private final ProductStockDto dto;
    @JsonIgnoreProperties(value = {"stockList","price",
            "createdBy","createdAt","updatedBy","updatedAt","category"})
    private Product product;
    @JsonProperty
    public double price(){
        return this.getProduct().getPrice().getSellPrice();
    }
    private  int quantity;
    @JsonProperty("categoryId")
    private  long id;
    private long shopId;


}

package com.example.supershop.dto.request;

import com.example.supershop.model.RequestLineItem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProductRequestDto extends RepresentationModel<ProductRequestDto> {
    private long requestId;
    private List<RequestLineItem> requestLineItems;
    private boolean isAccept;
    private String status;
    private int totalItem;
//    @JsonIgnoreProperties({"employee", "productList"})
//    private Shop requestShop;
//    @JsonIgnoreProperties({"manager", "products"})
//    private WareHouse wareHouse;
}

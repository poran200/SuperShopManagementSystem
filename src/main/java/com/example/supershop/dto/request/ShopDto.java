package com.example.supershop.dto.request;

import com.example.supershop.model.Address;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class ShopDto extends RepresentationModel<ShopDto> {

    private long shopId;
    private String shopName;
    private Address shopAddress;

}

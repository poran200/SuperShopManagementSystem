package com.example.supershop.standard.services;

import com.example.supershop.dto.request.ShopDto;
import com.example.supershop.dto.respose.Response;
import org.springframework.data.domain.Pageable;

public interface ShopService {
    Response create(ShopDto shopDto);

    Response getAllAndIsActiveTrue(Pageable pageable);

    Response getById(long id);

    Response getByName(String name);

    Response getByIdAndIsActiveTrue(long id);

    Response addProductInWareHouse(long wareHouseId, long productId);
}

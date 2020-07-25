package com.example.supershop.standard.services;

import com.example.supershop.dto.respose.Response;
import com.example.supershop.model.WareHouse;
import org.springframework.data.domain.Pageable;

public interface WareHouseService {
    Response  create(WareHouse wareHouse);
    Response  getAllAndIsActiveTrue(Pageable pageable);
    Response  getById(long id);
    Response  getByName(String name);
    Response  getByIdAndIsActiveTrue(long id);
    Response  addProductInWareHouse(long wareHouseId, long productId);
}

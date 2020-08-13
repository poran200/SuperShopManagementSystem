package com.example.supershop.standard.services;

import com.example.supershop.dto.request.ProductRequestDto;
import com.example.supershop.dto.respose.Response;
import com.example.supershop.model.ProductRequest;
import com.example.supershop.model.RequestLineItem;
import org.springframework.data.domain.Pageable;

public interface ProductRequestService {
    Response create(ProductRequestDto productRequestDto, RequestLineItem... lineItems);

    Response findById(long id);

    Response findAllByPage(Pageable pageable);

    Response findByShopId(long shopId, Pageable pageable);

    Response findByWarehouseId(long warehouseId, Pageable pageable);

    Response update(long id, ProductRequest productRequest);

    Response delete(long id);
}

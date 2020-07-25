package com.example.supershop.standard.services;

import com.example.supershop.dto.request.CreateProductRequestDto;
import com.example.supershop.dto.respose.Response;
import com.example.supershop.model.Product;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Response save(CreateProductRequestDto productRequestDto);
    Response update(Long Id, CreateProductRequestDto  productRequestDto);
    Response getByProductName(String productName);
    Response  delete(Long Id);
    Response  getById(Long Id);
    Product   getByIdIsActiveTrue(long id);
    Response getAll();
    Response getAll(Pageable pageable);
    Response getAllByProductIsActiveTrue(Pageable pageable);
    Response findAllByCategoryId(Pageable pageable, long categoryId);

}

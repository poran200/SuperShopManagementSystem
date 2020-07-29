package com.example.supershop.standard.services;

import com.example.supershop.dto.request.CategoryDto;
import com.example.supershop.dto.respose.Response;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Response  create(CategoryDto  categoryDto);
    Response  getById(long id);
    Response  getAllByPage(Pageable pageable);
    Response  deleteById(long id);
    Response  update(long id,CategoryDto categoryDto);
    Response   getByCategoryName(String name,Pageable pageable);

}

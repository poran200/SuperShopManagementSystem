package com.example.supershop.standard.services;

import com.example.supershop.dto.request.CategoryDto;
import com.example.supershop.dto.respose.Response;
import com.example.supershop.model.Category;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Response  create(CategoryDto  categoryDto);
    Response  getById(long id);
    Response  getAllByPage(Pageable pageable);
    Response  deleteById(long id);
    Response  update(long id,Category category);
    Response   getByCategoryName(String name,Pageable pageable);

}

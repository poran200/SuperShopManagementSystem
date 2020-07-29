package com.example.supershop.dto.request;

import com.example.supershop.model.Category;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CategoryDto {

    private long id;
    @NotEmpty(message = "category name not empty")
    private String  categoryName;
    private Category superCategory;
}

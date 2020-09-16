package com.example.supershop.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotEmpty;

@EqualsAndHashCode(callSuper = true)
@Data
public class CategoryDto extends RepresentationModel<CategoryDto> {

    private long id;
    @NotEmpty(message = "category name not empty")
    private String categoryName;
    private CategoryDto subCategory;
}

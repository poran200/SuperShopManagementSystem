package com.example.supershop.controller;

import com.example.supershop.anotation.APiController;
import com.example.supershop.anotation.DataValidation;
import com.example.supershop.dto.request.CategoryDto;
import com.example.supershop.dto.respose.Response;
import com.example.supershop.standard.services.CategoryService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.example.supershop.util.UrlConstrains.CategoryManagement;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@APiController
@RequestMapping(CategoryManagement.ROOT)
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping(CategoryManagement.CREATE)
    @DataValidation
    public ResponseEntity<Object> create(@RequestBody @Valid CategoryDto categoryDto, BindingResult bindingResult) {
        var response = categoryService.create(categoryDto);
        this.addLinkWithRel(response);
        return getResponseEntity(response);
    }

    @GetMapping(CategoryManagement.FIND_BY_ID)
    public ResponseEntity<Object> findById(@PathVariable long id) {
        var response = categoryService.getById(id);
        this.addLinkWithRel(response);
        return getResponseEntity(response);
    }

    @GetMapping(CategoryManagement.ALL_BY_PAGE)
    public ResponseEntity<Object> findByPage(Pageable pageable) {
        var response = categoryService.getAllByPage(pageable);
        if (response.getPage().hasContent()) {
            List<?> contents = response.getPage().getContent();
            contents.forEach(this::addLink);
        }
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }

    @GetMapping(CategoryManagement.FIND_BY_NAME)
    public ResponseEntity<Object> findByName(@PathVariable(value = "name") String name, Pageable pageable) {
        var response = categoryService.getByCategoryName(name.trim(), pageable);
        if (response.getPage().hasContent()) {
            List<?> contents = response.getPage().getContent();
            contents.forEach(this::addLink);
        }
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }

    @PutMapping(CategoryManagement.UPDATE)
    @DataValidation
    public ResponseEntity<Object> update(@PathVariable long id,
                                         @RequestBody @Valid CategoryDto categoryDto, BindingResult bindingResult) {
        var response = categoryService.update(id, categoryDto);
        this.addLinkWithRel(response);
        return getResponseEntity(response);
    }

    private ResponseEntity<Object> getResponseEntity(Response response) {
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }

    public CategoryDto addLink(Object dto) {
        CategoryDto categoryDto = (CategoryDto) dto;
        categoryDto.add(linkTo(methodOn(CategoryController.class)
                .findById(categoryDto.getId())).withSelfRel());
        categoryDto.add(linkTo(methodOn(ProductController.class)
                .getProductByPage(categoryDto.getId(), 0, 10)).withRel("products"));
        if (categoryDto.getSubCategory() != null) {
            categoryDto.add(linkTo(methodOn(CategoryController.class)
                    .findById(categoryDto.getSubCategory().getId())).withRel("subCategory"));
        }
        return categoryDto;
    }

    private void addLinkWithRel(Response response) {
        if (response.getContent() != null) {
            response.setContent(addLink(response.getContent()));
        }

    }

}

package com.example.supershop.controller;

import com.example.supershop.anotation.APiController;
import com.example.supershop.anotation.DataValidation;
import com.example.supershop.dto.request.CategoryDto;
import com.example.supershop.dto.respose.Response;
import com.example.supershop.standard.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.supershop.util.UrlConstrains.CategoryManagement;

@APiController
@RequestMapping(CategoryManagement.ROOT)
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping(CategoryManagement.CREATE)
    @DataValidation
    public ResponseEntity<Object> create(@RequestBody @Valid CategoryDto categoryDto , BindingResult bindingResult){
        var response = categoryService.create(categoryDto);
        return getResponseEntity(response);
      }
     @GetMapping(CategoryManagement.FIND_BY_ID)
     public ResponseEntity<Object>findById(@PathVariable long id){
         var response = categoryService.getById(id);
         return getResponseEntity(response);
      }

      @PutMapping(CategoryManagement.UPDATE)
      @DataValidation
      public ResponseEntity<Object> update(@PathVariable long id,
                                           @RequestBody @Valid CategoryDto categoryDto, BindingResult bindingResult){
          var response = categoryService.update(id, categoryDto);
          return getResponseEntity(response);
      }

     private ResponseEntity<Object> getResponseEntity(Response response){
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
     }



     //Todo getByNameByPageAbel
    //Todo getAllByPage
}

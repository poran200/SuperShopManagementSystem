package com.example.supershop.controller;

import com.example.supershop.anotation.APiController;
import com.example.supershop.anotation.DataValidation;
import com.example.supershop.dto.request.CreateProductRequestDto;
import com.example.supershop.dto.respose.Response;
import com.example.supershop.standard.services.ProductService;
import com.example.supershop.util.UrlConstrains;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@APiController
@RequestMapping(UrlConstrains.ProductManagement.ROOT)
public class ProductController {
   private final ProductService productsService;

    public ProductController(ProductService productsService) {

        this.productsService = productsService;
    }

    @PostMapping(UrlConstrains.ProductManagement.CREATE)
    @DataValidation
    public ResponseEntity<Response> createProduct(@Valid @RequestBody CreateProductRequestDto productRequestDto, BindingResult result) {
        Response response = productsService.save(productRequestDto);
        return getResponse(response);
    }

    @GetMapping(UrlConstrains.ProductManagement.FIND_BY_ID)
    public ResponseEntity<Response> findProduct(@PathVariable long id) {
        Response response = productsService.getById(id);
        return getResponse(response);
    }

    private ResponseEntity<Response> getResponse(Response response) {
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }

    @PutMapping(UrlConstrains.ProductManagement.UPDATE)
    @DataValidation
    public ResponseEntity<Response> update(@PathVariable long id, @RequestBody CreateProductRequestDto dto,
                                           BindingResult bindingResult) {
        var response = productsService.update(id, dto);
        return getResponse(response);

    }

    @GetMapping(value = UrlConstrains.ProductManagement.CATEGORY + "/{id}", params = {"page", "size"})
    public ResponseEntity<Response> getProductByPage(@PathVariable long id,
                                                     @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                     @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        if (size == 0) {
            size = 10;
        }
        Pageable pageable = PageRequest.of(page, size);
        Response response = productsService.findAllByCategoryId(pageable, id);
        return getResponse(response);
    }


}

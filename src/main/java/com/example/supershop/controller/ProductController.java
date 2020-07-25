package com.example.supershop.controller;

import com.example.supershop.anotation.APiController;
import com.example.supershop.dto.request.CreateProductRequestDto;
import com.example.supershop.dto.respose.ProductResponseDto;
import com.example.supershop.dto.respose.Response;
import com.example.supershop.standard.services.ProductService;
import com.example.supershop.util.ResponseBuilder;
import com.example.supershop.util.UrlConstrains;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@APiController
@RequestMapping(UrlConstrains.ProductManagement.ROOT)
public class ProductController {
   private final ProductService productsService;

    public ProductController(ProductService productsService) {

        this.productsService = productsService;
    }

    @PostMapping(UrlConstrains.ProductManagement.CREATE)
    public ResponseEntity<Object> createProduct(@Valid @RequestBody CreateProductRequestDto productRequestDto, BindingResult result){
        if (result.hasFieldErrors()){
            return ResponseEntity.badRequest().body(ResponseBuilder.getFailureResponse(result,"Bean validation error !"));
        }else {
            Response response = productsService.save(productRequestDto);
            return  ResponseEntity.status((int) response.getStatusCode()).body(response);
        }
    }
    @GetMapping(UrlConstrains.ProductManagement.FIND_BY_ID)
    public ResponseEntity<Response> findProduct(@PathVariable long id){
        Response response = productsService.getById(id);
        if(response.getContent()!= null) {
            ProductResponseDto productResponseDto = (ProductResponseDto) response.getContent();
            productResponseDto.add(linkTo(ProductController.class).slash(productResponseDto.getId()).withSelfRel());
            Link categoryLink = linkTo(methodOn(ProductController.class).getProductByPage(productResponseDto.getCategoryId(),0,0)).withRel("category");
            productResponseDto.add(categoryLink);
            response.setContent(productResponseDto);
        }
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }
    @GetMapping(value = UrlConstrains.ProductManagement.CATEGORY+"/{id}",params = {"page","size"})
    public ResponseEntity<Response> getProductByPage(@PathVariable long id,
                                                     @RequestParam("page") int page,@RequestParam("size") int size ){
        if (size ==0 ){
            size = 10;
        }
        Pageable pageable = PageRequest.of(page,size);
        Response response = productsService.findAllByCategoryId(pageable,id);
        return  ResponseEntity.ok(response);
    }
}

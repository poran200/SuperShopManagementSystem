package com.example.supershop.controller;

import com.example.supershop.anotation.APiController;
import com.example.supershop.anotation.DataValidation;
import com.example.supershop.dto.request.ProductRequestDto;
import com.example.supershop.model.ProductRequest;
import com.example.supershop.standard.services.ProductRequestService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.supershop.util.UrlConstrains.ProductRequestManagement;

@APiController
@RequestMapping(ProductRequestManagement.ROOT)
public class ProductRequestController {
    private final ProductRequestService productRequestService;
    private final ModelMapper modelMapper;

    public ProductRequestController(ProductRequestService productRequestService, ModelMapper modelMapper) {
        this.productRequestService = productRequestService;
        this.modelMapper = modelMapper;
    }

    @PostMapping(ProductRequestManagement.CREATE)
    public ResponseEntity<Object> create(@Valid @RequestBody ProductRequestDto productRequestDto, BindingResult result) {
        var response = productRequestService.create(productRequestDto);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }

    @GetMapping(ProductRequestManagement.FIND_BY_ID)
    public ResponseEntity<Object> findById(@PathVariable long id) {
        var response = productRequestService.findById(id);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }

    @GetMapping(ProductRequestManagement.ALL_BY_PAGE)
    public ResponseEntity<Object> findAllByPage(@RequestParam(required = false) Pageable pageable) {
        var response = productRequestService.findAllByPage(pageable);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);

    }

    @GetMapping(ProductRequestManagement.FIND_BY_ShopId)
    public ResponseEntity<Object> findByShopId(@PathVariable long id, @RequestParam(required = false) Pageable pageable) {
        var response = productRequestService.findByShopId(id, pageable);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }

    @GetMapping(ProductRequestManagement.FIND_BY_WarehouseId)
    public ResponseEntity<Object> findByWarehouseId(@PathVariable long id, @RequestParam(required = false) Pageable pageable) {
        var response = productRequestService.findByWarehouseId(id, pageable);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }

    @PutMapping(ProductRequestManagement.UPDATE)
    @DataValidation
    public ResponseEntity<Object> update(@PathVariable long id,
                                         @RequestBody @Valid ProductRequestDto requestDto,
                                         BindingResult bindingResult) {
        var productRequest = modelMapper.map(requestDto, ProductRequest.class);
        var response = productRequestService.update(id, productRequest);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }

    @DeleteMapping(ProductRequestManagement.DELETE)
    public ResponseEntity<Object> delete(@PathVariable long id) {
        var response = productRequestService.delete(id);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }

}

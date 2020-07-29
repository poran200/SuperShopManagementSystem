package com.example.supershop.controller;

import com.example.supershop.anotation.APiController;
import com.example.supershop.anotation.DataValidation;
import com.example.supershop.dto.request.CreateStockRequestDto;
import com.example.supershop.dto.respose.Response;
import com.example.supershop.standard.services.WarehouseStockService;
import com.example.supershop.util.UrlConstrains;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@APiController
@RequestMapping(UrlConstrains.WarehouseStockManagement.ROOT)
public class WarehouseStockController {
    private final WarehouseStockService stockService;

    public WarehouseStockController(WarehouseStockService stockService) {
        this.stockService = stockService;
    }
    @PostMapping(UrlConstrains.WarehouseStockManagement.CREATE)
    @DataValidation
    public ResponseEntity<Response> create(@RequestBody @Valid CreateStockRequestDto requestDto , BindingResult result){
        var response = stockService.create(requestDto);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }

    @GetMapping(UrlConstrains.WarehouseStockManagement.FIND_BY_ID)
    public ResponseEntity<Object> getByIdAndIsActive(@PathVariable long id){
        var res = stockService.getByIdAndIsActive(id);
        return ResponseEntity.status((int) res.getStatusCode()).body(res);
    }
    @GetMapping(UrlConstrains.WarehouseStockManagement.ALL_BY_PAGE)
    public ResponseEntity<Object> getAllWarehouseIdAndByPage(@PathVariable long warehouseId,
                                                             @RequestParam(required = false) Pageable pageable){
        var response = stockService.getAllByWarehouseIdAndByPage(warehouseId, pageable);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }
    @GetMapping(UrlConstrains.WarehouseStockManagement.FIND_BY_WAREHOUSE_ID_AND_PRODUCT_ID)
    public ResponseEntity<Object> getWarehouseIdAndProductId(@PathVariable long warehouseId,@PathVariable long productId){
        var response = stockService.getByWarehouseIdAndByProductId(warehouseId, productId);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }

    @DeleteMapping(UrlConstrains.WarehouseStockManagement.DELETE)
    public ResponseEntity<Object> deleteById(@PathVariable long id) {
        var response = stockService.deleteStock(id);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }

}

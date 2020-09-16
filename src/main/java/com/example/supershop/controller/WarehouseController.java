package com.example.supershop.controller;

import com.example.supershop.anotation.APiController;
import com.example.supershop.dto.request.AddProductRequestDto;
import com.example.supershop.model.WareHouse;
import com.example.supershop.standard.services.WareHouseService;
import com.example.supershop.util.ResponseBuilder;
import com.example.supershop.util.UrlConstrains.WarehouseManagement;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@APiController
@RequestMapping(WarehouseManagement.ROOT)
public class WarehouseController {
    private final WareHouseService wareHouseService;

    public WarehouseController(WareHouseService wareHouseService) {
        this.wareHouseService = wareHouseService;
    }
    @PostMapping(WarehouseManagement.CREATE)
    public ResponseEntity<Object> create(@RequestBody  WareHouse wareHouse){
        var response = wareHouseService.create(wareHouse);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }
    @GetMapping(WarehouseManagement.FIND_BY_ID)
    public ResponseEntity<Object> findById(@PathVariable long id) {
        var re = wareHouseService.getByIdAndIsActiveTrue(id);
        return ResponseEntity.status((int) re.getStatusCode()).body(re);
    }

    @GetMapping(WarehouseManagement.ALL_BY_PAGE)
//    @PageAbleData
    public ResponseEntity<Object> findAllByPage(@RequestParam(required = false) Pageable pageable) {
        var response = wareHouseService.getAllAndIsActiveTrue(pageable);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }

    @GetMapping(WarehouseManagement.FIND_BY_NAME)
    public ResponseEntity<Object> findByName(@PathVariable String name) {
        var response = wareHouseService.getByName(name);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }

   @PostMapping(WarehouseManagement.ADD_PRODUCT)
   public  ResponseEntity<Object> addProductOnWarehouse(@RequestBody  AddProductRequestDto request, BindingResult result){
        if (result.hasErrors()){
            return ResponseEntity.badRequest().body(ResponseBuilder.getFailureResponse(result,"Validation error!"));
        }
       var response = wareHouseService.addProductInWareHouse(request.getWarehouseId(), request.getProductId());
       return ResponseEntity.status((int) response.getStatusCode()).body(response);
   }
}

package com.example.supershop.controller;

import com.example.supershop.anotation.APiController;
import com.example.supershop.anotation.DataValidation;
import com.example.supershop.dto.request.ShopDto;
import com.example.supershop.dto.respose.Response;
import com.example.supershop.standard.services.ShopService;
import com.example.supershop.util.UrlConstrains;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@APiController
@RequestMapping(UrlConstrains.ShopManagement.ROOT)
public class ShopController {
    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @PostMapping(UrlConstrains.ShopManagement.CREATE)
    @DataValidation
    public ResponseEntity<Object> create(@RequestBody ShopDto shopDto, BindingResult result) {
        var response = shopService.create(shopDto);
        return getResponse(response);
    }

    @GetMapping(UrlConstrains.ShopManagement.FIND_BY_ID)
    public ResponseEntity<Object> getBYId(@PathVariable long id) {
        var response = shopService.getById(id);
        return getResponse(response);
    }

    private ResponseEntity<Object> getResponse(Response response) {
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }
}

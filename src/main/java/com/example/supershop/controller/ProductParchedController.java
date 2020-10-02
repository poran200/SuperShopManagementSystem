package com.example.supershop.controller;

import com.example.supershop.anotation.APiController;
import com.example.supershop.anotation.DataValidation;
import com.example.supershop.dto.request.PurchaseInvoiceRequest;
import com.example.supershop.standard.services.ParchesService;
import com.example.supershop.util.UrlConstrains;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@APiController
@RequestMapping(UrlConstrains.PurchaseManagement.ROOT)
public class ProductParchedController {
    private final ParchesService parchesService;

    public ProductParchedController(ParchesService parchesService) {
        this.parchesService = parchesService;
    }

    @DataValidation
    @PostMapping(UrlConstrains.PurchaseManagement.PRODUCT_PARCHED)
    public ResponseEntity<Object> productParched(@RequestBody PurchaseInvoiceRequest request, BindingResult result) {
        var response = parchesService.crateParchesInvoice(request);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }
}

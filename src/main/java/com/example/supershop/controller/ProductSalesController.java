package com.example.supershop.controller;

import com.example.supershop.anotation.APiController;
import com.example.supershop.anotation.DataValidation;
import com.example.supershop.dto.request.SalesInvoiceRequest;
import com.example.supershop.dto.respose.Response;
import com.example.supershop.standard.services.ProductSaleService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import static com.example.supershop.util.UrlConstrains.SalesManagement;

@APiController
@RequestMapping(SalesManagement.ROOT)
public class ProductSalesController {

    private final ProductSaleService productSaleService;

    public ProductSalesController(ProductSaleService productSaleService) {
        this.productSaleService = productSaleService;
    }

    @PostMapping(SalesManagement.CREATE_INVOICE)
    @DataValidation
    public ResponseEntity<Object> createINovice(@Valid @RequestBody SalesInvoiceRequest request, BindingResult result){
        Response response = productSaleService.createSaleInvoice(request);
         return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }

//    @GetMapping(UrlConstrains.SalesManagement.FIND_BY_ID)
//    public ResponseEntity<Object> geInvoiceById(@PathVariable int invoiceId){
//        var response = productSaleService.getInvoiceById(invoiceId);
//        return ResponseEntity.status((int) response.getStatusCode()).body(response);
//    }
}

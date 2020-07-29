package com.example.supershop.controller;


import com.example.supershop.anotation.DataValidation;
import com.example.supershop.dto.request.SalesInvoiceRequest;
import com.example.supershop.model.Product;
import com.example.supershop.model.SaleInvoiceLineItem;
import com.example.supershop.model.SalesInvoice;
import com.example.supershop.services.SalesService;
import com.example.supershop.standard.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sale")
public class SalesController {
    private final SalesService salesService;
    private final ProductService productsService;

    public SalesController(SalesService salesService, ProductService productsService) {
        this.salesService = salesService;
        this.productsService = productsService;
    }

    @PostMapping
    @DataValidation
    public ResponseEntity<Object> createInvoice(@Valid @RequestBody SalesInvoiceRequest invoiceRequest, BindingResult bindingResult) {
        List<SaleInvoiceLineItem> saleInvoiceLineItems = new ArrayList<>();

        System.out.println("invoiceRequest = " + invoiceRequest);
         if(invoiceRequest.getItemLineRequests().isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("At last one select product for sale!");

         } else {

             try {
                 invoiceRequest.getItemLineRequests().forEach(itemLineRequest -> {
                     Product product = productsService.getByIdIsActiveTrue(itemLineRequest.getProductId());
                     saleInvoiceLineItems.add(new SaleInvoiceLineItem(product,itemLineRequest.getQuantity()));
                 });
                 SalesInvoice salesInvoice = salesService.createSalesInvoice(saleInvoiceLineItems, invoiceRequest.getShopId(), invoiceRequest.getUserId());
                 return ResponseEntity.ok(salesInvoice);
             } catch (Exception e ){
                 return    ResponseEntity.badRequest().body(e.getMessage());
             }
         }

    }
}

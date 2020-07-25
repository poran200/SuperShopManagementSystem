package com.example.supershop.controller;

import com.example.supershop.anotation.APiController;
import com.example.supershop.dto.request.SaleInvoiceUpdateRequestDto;
import com.example.supershop.dto.respose.Response;
import com.example.supershop.model.SalesInvoice;
import com.example.supershop.standard.services.SaleInvoiceService;
import com.example.supershop.util.UrlConstrains.InvoiceManagement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@APiController
@RequestMapping(InvoiceManagement.ROOT)
public class SalesInvoiceController {
   private final SaleInvoiceService saleInvoiceService;
    private SalesInvoice salesInvoice;

    public SalesInvoiceController(SaleInvoiceService saleInvoiceService) {
        this.saleInvoiceService = saleInvoiceService;
    }

    @GetMapping(InvoiceManagement.FIND_BY_ID)
    public ResponseEntity<Object> findById(@PathVariable int invoiceId){
        var response = saleInvoiceService.getById(invoiceId);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }
    @GetMapping(InvoiceManagement.FIND_BY_SHOP_ID)
    public ResponseEntity<Object> findAllByShopId(@PathVariable long shopId){
        var re = saleInvoiceService.findAllByShopId(shopId);
        return ResponseEntity.status((int) re.getStatusCode()).body(re);
    }


    //Todo fix the update invoice
    @PutMapping(InvoiceManagement.UPDATE)
    public ResponseEntity<Response> updateInvoice(@PathVariable int invoiceId, @RequestBody SaleInvoiceUpdateRequestDto updateRequestDto){
        var response = saleInvoiceService.update(invoiceId, updateRequestDto);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }

    @DeleteMapping(value = InvoiceManagement.DELETE_INVOICE_LINE_ITEM)
    public ResponseEntity<Object> deleteInvoiceLineItem(@PathVariable int invoiceId,@PathVariable long lineItemId ){
        var response = saleInvoiceService.deleteInvoiceLineItem(invoiceId, lineItemId);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }

}

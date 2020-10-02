package com.example.supershop.controller;

import com.example.supershop.anotation.APiController;
import com.example.supershop.dto.respose.Response;
import com.example.supershop.standard.services.PurchaseInvoiceService;
import com.example.supershop.util.UrlConstrains;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@APiController
@RequestMapping(UrlConstrains.PurchaseInvoiceManagement.ROOT)
public class PurchaseInvoiceController {
    private final PurchaseInvoiceService purchaseInvoiceService;

    @Autowired
    public PurchaseInvoiceController(PurchaseInvoiceService purchaseInvoiceService) {
        this.purchaseInvoiceService = purchaseInvoiceService;
    }

    @GetMapping(UrlConstrains.PurchaseInvoiceManagement.FIND_BY_ID)
    public ResponseEntity<Object> findById(@PathVariable long id) {
        var re = purchaseInvoiceService.getById(id);
        return getResponse(re);
    }

    @GetMapping(UrlConstrains.PurchaseInvoiceManagement.ALL_BY_PAGE)
    public ResponseEntity<Object> findAllWithPage(Pageable pageable) {
        var allByPage = purchaseInvoiceService.getAllByPage(pageable);
        return getResponse(allByPage);
    }

    @DeleteMapping(UrlConstrains.PurchaseInvoiceManagement.DELETE)
    public ResponseEntity<Object> deleteById(@PathVariable long id) {
        return getResponse(purchaseInvoiceService.delete(id));
    }

    @DeleteMapping(UrlConstrains.PurchaseInvoiceManagement.DELETE_INVOICE_ITEM)
    public ResponseEntity<Object> deleteInvoiceItem(@PathVariable long invoiceId, @PathVariable long itemId) {
        return getResponse(purchaseInvoiceService.deleteInvoiceLineItem(invoiceId, itemId));
    }

    @GetMapping(UrlConstrains.PurchaseInvoiceManagement.ALL_BY_WAREHOUSE)
    public ResponseEntity<Object> getAllByWareHouse(@PathVariable long warehouseId, @RequestParam(required = false) Pageable pageable) {
        return getResponse(purchaseInvoiceService.findAllByWarehouseId(warehouseId, pageable));
    }

    @NotNull
    private ResponseEntity<Object> getResponse(Response re) {
        return ResponseEntity.status((int) re.getStatusCode()).body(re);
    }
}

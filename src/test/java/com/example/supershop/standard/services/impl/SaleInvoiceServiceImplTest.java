package com.example.supershop.standard.services.impl;

import com.example.supershop.model.SalesInvoice;
import com.example.supershop.standard.services.SaleInvoiceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
class SaleInvoiceServiceImplTest {
    @Autowired
    private  SaleInvoiceService saleInvoiceService;

    @Test
    void getById() {
    }

    @Test
    void getAllByPage() {
    }

    @Test
    void update() {
        var response = saleInvoiceService.getById(1);
        SalesInvoice content = (SalesInvoice) response.getContent();
        System.out.println(content);
        content.getInvoiceItems().forEach(lineItem -> lineItem.setItemQuantity(lineItem.getItemQuantity() + 1));
        System.out.println(content.getInvoiceItems());
//        saleInvoiceService.update(1,content);

    }

    @Test
    void delete() {
    }
}
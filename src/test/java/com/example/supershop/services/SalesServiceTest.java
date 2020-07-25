package com.example.supershop.services;

import com.example.supershop.model.Product;
import com.example.supershop.model.SaleInvoiceLineItem;
import com.example.supershop.model.SalesInvoice;
import com.example.supershop.standard.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
class SalesServiceTest {

    @Autowired
    private  SalesService salesService;
    @Autowired
    private ProductService productsService;

    @Test
    void createSalesInvoice() {
        Product product = productsService.getByIdIsActiveTrue(2354L);
        List<SaleInvoiceLineItem> lineItems = new ArrayList<>();
         lineItems.add(new SaleInvoiceLineItem(product,5));
       SalesInvoice salesInvoice = salesService.createSalesInvoice(lineItems, 103,101);
//        assertThrows(StockNOtAvailabelException.class, ()-> {
//            SalesInvoice salesInvoice = salesService.createSalesInvoice(lineItems, 103, 101);
//            System.out.println("salesInvoice = " + salesInvoice);
//
//        } );
        assertEquals(10,salesInvoice.getSaleInvoiceId());
    }
}
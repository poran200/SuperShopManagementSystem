package com.example.supershop;

import com.example.supershop.standard.services.SaleInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SuperShopApplication  {

    @Autowired
    SaleInvoiceService saleInvoiceService;
    public static void main(String[] args) {
        SpringApplication.run(SuperShopApplication.class, args);


      }


/**
    @Override
    @Transactional
    public void run(String... args) throws Exception {
        var response = saleInvoiceService.getById(1);
        SalesInvoice content = (SalesInvoice) response.getContent();
        System.out.println("content = " + content);
        content.getInvoiceItems().forEach(lineItem -> lineItem.setProductQuantity(lineItem.getProductQuantity()+1));
        var update = saleInvoiceService.update(1, content);
        System.out.println("update = " + update);
    }
*/
}




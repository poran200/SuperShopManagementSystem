package com.example.supershop.services;

import com.example.supershop.dto.respose.Response;
import com.example.supershop.exception.EntityAlreadyExistException;
import com.example.supershop.exception.EntityNotFoundException;
import com.example.supershop.model.Stock;
import com.example.supershop.standard.services.ShopStockService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class StockServiceTest {

    @Autowired
    ShopStockService stockService;
    @Test
    void createStock() throws EntityNotFoundException, EntityAlreadyExistException {
        Stock stock = new Stock(6);
        Response getStock = stockService.createStock(103, 2355, stock);
        var content =(Stock) getStock.getContent();
        assertEquals(103,content.getShop().getShopId());

    }

//    @Test
//    void updateStock() {
//        Stock stock = stockService.updateStock(103, 2354, 1);
//        assertEquals(2,stock.getQuantity());
//    }
}
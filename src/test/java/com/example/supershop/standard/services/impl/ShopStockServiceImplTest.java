package com.example.supershop.standard.services.impl;

import com.example.supershop.standard.services.ShopStockService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
class ShopStockServiceImplTest {
    @Autowired
    private ShopStockService shopStockService;

    @Test
    void createStock() {
    }

    @Test
    void getStockById() {
    }

    @Test
    void getStockByPage() {
    }

    @Test
    void updateStock() {
    }

    @Test
    void testUpdateStock() {
    }

    @Test
    void testUpdateStock1() {
        var stock = shopStockService.updateStock(1, 2);
        assertEquals(4,stock.getQuantity());
    }

    @Test
    void findByShopIdAndProductId() {
        var stock = shopStockService.findByShopIdAndProductId(103, 2354);
        assertEquals(1,stock.getStockId());
        assertEquals(2,stock.getQuantity());
    }
}
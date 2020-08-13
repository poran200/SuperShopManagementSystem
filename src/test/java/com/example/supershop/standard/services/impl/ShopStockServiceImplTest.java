package com.example.supershop.standard.services.impl;

import com.example.supershop.dto.request.StockDto;
import com.example.supershop.exception.EntityAlreadyExistException;
import com.example.supershop.standard.services.ShopStockService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ShopStockServiceImplTest {
    @Autowired
    private ShopStockService shopStockService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

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
        assertEquals(4, stock.getQuantity());
    }

    @Test
    void findByShopIdAndProductId() {
        var stock = shopStockService.findByShopIdAndProductId(103, 2354);
        assertEquals(1, stock.getStockId());
        assertEquals(2, stock.getQuantity());
    }

    @Test
    void saveTest() throws EntityAlreadyExistException {
        var stockDto = new StockDto();
        var product = new StockDto.Product();
        product.setProductId(53);
        var shop = new StockDto.Shop();
        shop.setShopId(103);
        stockDto.setProduct(product);
        stockDto.setShop(shop);
        stockDto.setQuantity(5);
        var saveStock = shopStockService.createStock(stockDto);

    }
}
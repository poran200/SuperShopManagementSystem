package com.example.supershop.standard.services.impl;

import com.example.supershop.model.Address;
import com.example.supershop.model.WareHouse;
import com.example.supershop.standard.services.WareHouseService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
class WareHouseServiceImplTest {

   @Autowired
    WareHouseService wareHouseService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void create() {
        WareHouse wareHouse = new WareHouse();
        wareHouse.setName("comilla sason para");
        wareHouse.setAddress(new Address("sasongasa ","noapara  rode 23"));
        var response = wareHouseService.create(wareHouse);
        assertEquals(201,response.getStatusCode());
    }

    @Test
    void getAllAndIsActiveTrue() {
    }

    @Test
    void getById() {
    }

    @Test
    void getByName() {
    }

    @Test
    void getByIdAndIsActiveTrue() {
    }

    @Test
    void addProductInWareHouse() {

    }
}
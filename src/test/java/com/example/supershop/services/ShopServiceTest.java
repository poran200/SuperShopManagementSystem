package com.example.supershop.services;

import com.example.supershop.enam.Gender;
import com.example.supershop.exception.EntityAlreadyExistException;
import com.example.supershop.exception.EntityNotFoundException;
import com.example.supershop.model.Address;
import com.example.supershop.model.Employee;
import com.example.supershop.model.Name;
import com.example.supershop.model.Shop;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ShopServiceTest {
    @Autowired
    ShopService shopService;
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createShop() throws EntityAlreadyExistException {
        Shop shop = new Shop(103,"Comilla-2",null,null,null);
        shop.setShopAddress(new Address("comilla","Dormopur road no -20"));
        Name name = new Name("poran","chowdury");
        Address address = new Address( "dhaka", "nikonjo");
        List<String> list = new ArrayList<>();
        list.add("01757414897");
        Employee employee  = new Employee(2001, name, new Date(), "p@email.com",list,new java.util.Date(),
                Gender.MALE, "accountant", address, null, null);
        shop.setEmployee(employee);
        Shop saveShop = shopService.createShop(shop);
        assertEquals(saveShop.getShopId(),shop.getShopId());
        assertEquals(saveShop.getShopName(), shop.getShopName());
    }

    @Test
    void findAllShop() {
        List<Shop> shopList = shopService.findAllShop();
        assertEquals(2,shopList.size());
    }

    @Test
    void findById() throws EntityNotFoundException {
        Optional<Shop> shop = shopService.findById(103);
        assertEquals(103,
                shop.get().getShopId());
    }

    @Test
    void finnShopByCity() {
        List<Shop> shopByCity = shopService.finnShopByCity("comilla");
         assertEquals("comilla",shopByCity.get(0).getShopAddress().getCity());
    }

    @Test
    void updateShop() {
    }

    @Test()
    void deleteShopById() {
        assertThrows(EntityNotFoundException.class, ()->shopService.deleteShop(104));
    }

    @Test
    void addProduct() throws EntityNotFoundException {

        for (int i = 20; i <44 ; i++) {
            shopService.addProductInShop(103, i);
        }


    }
}
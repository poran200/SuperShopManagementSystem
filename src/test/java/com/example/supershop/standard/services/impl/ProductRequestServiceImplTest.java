package com.example.supershop.standard.services.impl;

import com.example.supershop.repository.ProductRepository;
import com.example.supershop.repository.ShopRepository;
import com.example.supershop.repository.WareHouseRepository;
import com.example.supershop.standard.services.ProductRequestService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductRequestServiceImplTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private WareHouseRepository wareHouseRepository;
    @Autowired
    private ProductRequestService productRequestService;

    @BeforeEach
    void setUp() {
    }

//    @Test
//    void create() {
//        var product = productRepository.getOne(50L);
//        var shop = shopRepository.getOne(103L);
//        var wareHouse = wareHouseRepository.getOne(1L);
//        List<RequestLineItem> requestLineItems = new ArrayList<>();
//        requestLineItems.add(new RequestLineItem(56, product, 5, null));
//        var productRequest = new ProductRequestDto(1, requestLineItems, false, "send", requestLineItems.size(), shop, wareHouse);
//        var response = productRequestService.create(productRequest);
//        System.out.println(response);
//    }

    @Test
    void findById() {
    }

    @Test
    void findAllByPage() {
    }

    @Test
    void findByShopId() {
    }

    @Test
    void findByWarehouseId() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testCreate() {
    }

    @Test
    void testFindById() {
    }

    @Test
    void testFindAllByPage() {
    }

    @Test
    void testFindByShopId() {
    }

    @Test
    void testFindByWarehouseId() {
    }

    @Test
    void testUpdate() {
    }

    @Test
    void testDelete() {
    }
}
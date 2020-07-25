package com.example.supershop;

import com.example.supershop.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SuperShopApplicationTests {
    @Autowired
    RoleRepository roleRepository;

    @Test
    void contextLoads() {

    }



}

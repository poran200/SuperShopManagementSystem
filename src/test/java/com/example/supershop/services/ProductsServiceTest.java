package com.example.supershop.services;

import com.example.supershop.dto.request.CreateProductRequestDto;
import com.example.supershop.dto.respose.Response;
import com.example.supershop.exception.EntityAlreadyExistException;
import com.example.supershop.model.Category;
import com.example.supershop.model.Product;
import com.example.supershop.repository.CategoryRepository;
import com.example.supershop.repository.ProductRepository;
import com.example.supershop.standard.services.ProductService;
import com.github.javafaker.Book;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
class ProductsServiceTest {

    @Autowired
    ProductService productsService;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryService categoryService;
    @Autowired
    Faker  faker;
    @Autowired
    ProductRepository productRepository;
    Logger logger = LoggerFactory.getLogger(ProductsServiceTest.class);
    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }
    Product product = new Product(2356, "Realmi 5i", "smart phone", new Date(),
            new Product.Price(10000, 12500), 5.0, null);

    @Test
    void saveCategory() {

//         categoryService.deleteAllProducts(4);
//         categoryRepository.deleteById(4L);
        Category category = new Category(4, "Test category", null,null,null);
        categoryRepository.save(category);
        assertEquals(4,category.getId());
    }

    @Test
//    @Disabled("Disable until create porduct1 medhot test")
    void createProduct() throws EntityAlreadyExistException {
        CreateProductRequestDto dto = new CreateProductRequestDto();
        dto.setId(product.getProductId());
        dto.setCategoryId(4);
        dto.setDetails(product.getDetails());
        dto.setPrice(product.getPrice());
        dto.setExpireDate(product.getExpireDate());
        dto.setProductName(product.getProductName());
        Response response = productsService.save(dto);
        var saveProduct = (Product)response.getContent();
        assertEquals(2356,saveProduct.getProductId());
        assertEquals(4,saveProduct.getCategory().getId());
    }


    @Test
    void findProductById() {
        Product productById = productsService.getByIdIsActiveTrue(2354);
        assertEquals(2354,productById.getProductId());
    }

    @Test
    @Disabled
    void findProductByName() {
        List<Product> product = (List<Product>) productsService.getByProductName("chips").getContent();
        assertEquals("chips",product.get(0).getProductName().toLowerCase());
    }

//    @Test
//    void findAllProductByShopId() {
//        long shopId = 103;
//        List<Product> allProductByShopId = productsService.(shopId);
//        assertEquals(shopId,allProductByShopId.get(0).getStockList().get(0).getShop().getShopId());
//    }

    @Test
    @Disabled
    void Faker() {
        Name name = faker.name();
        Book book = faker.book();
        String productName = faker.commerce().productName();
        Category category = categoryRepository.getOne(4L);
        List<Product> products= new ArrayList<>();
         long idStart =50;
        for (int i = 0; i < 50; i++) {
             TimeUnit timeUnit = TimeUnit.DAYS;

            Product product = new Product(idStart,
                    faker.commerce().productName(),
                    faker.lorem().sentence(),
                    faker.date().future(50, timeUnit)
                    , new Product.Price(Double.parseDouble(faker.commerce().price()), Double.parseDouble(faker.commerce().price()))
                    ,Double.parseDouble( faker.commerce().price(10, 50)), category
            );
            products.add(product);
            System.out.println("product = " + product);
            idStart++;

        }
        var saveAllProduct = productRepository.saveAll(products);
        assertEquals(products,saveAllProduct);
    }

//    @Test
//    void oneToManySaveTest() {
////        productRepository.deleteById(2356L);
//        var product = productsService.createProduct1(4, this.product);
//        logger.info("product {}",product);
//        assertEquals(2356,product.getProductId());
//    }
}
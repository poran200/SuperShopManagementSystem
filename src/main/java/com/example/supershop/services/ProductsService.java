package com.example.supershop.services;

import com.example.supershop.exception.EntityAlreadyExistException;
import com.example.supershop.model.Category;
import com.example.supershop.model.Product;
import com.example.supershop.model.Stock;
import com.example.supershop.repository.CategoryRepository;
import com.example.supershop.repository.ProductRepository;
import com.example.supershop.repository.ShopRepository;
import com.example.supershop.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class  ProductsService {
    private  final ProductRepository productRepository;
    private final StockRepository stockRepository;
    private final ShopRepository shopRepository;
    private final CategoryRepository categoryRepository;

    Logger logger = LoggerFactory.getLogger(ProductsService.class);

    public ProductsService(ProductRepository productRepository,
                           StockRepository stockRepository,
                           ShopRepository shopRepository,
                           CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.stockRepository = stockRepository;
        this.shopRepository = shopRepository;
        this.categoryRepository = categoryRepository;
    }

    public Product createProduct(long categoryId,Product product) throws EntityAlreadyExistException {
        Optional<Product> optionalProduct = productRepository.findById(product.getProductId());
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
       // new Product();
        Product saveProduct;
         if (optionalCategory.isPresent()) {
             logger.info("category found ");
             optionalCategory.get().addProduct(product);
             if (optionalProduct.isPresent()) {
                 throw new EntityAlreadyExistException(product);
             }else {
                 product.setCategory(optionalCategory.get());
                saveProduct = productRepository.save(product);
                logger.info("product save {}",saveProduct);
             }
             categoryRepository.save(optionalCategory.get());
         }else {
             throw  new EntityNotFoundException(categoryId+"");
         }
         return saveProduct;
    }
    public Optional<Product> findProductById(long productId) throws EntityNotFoundException{
        Optional<Product> getProduct = productRepository.findById(productId);
        if (getProduct.isPresent()) return getProduct;
        else  throw new  EntityNotFoundException("Product not found : "+productId);
    }

    public List<Product> findProductByName(String productName){
       return productRepository.findByProductName(productName);
    }


    public List<Product> saveAllProduct(List<Product> products){
        return productRepository.saveAll(products);
    }

//    public List<Product> findAllProductByShopId(long shopId){
//
//        List<Product> allProductsByShopId = shopRepository.getOne(shopId).getProductList();
//          allProductsByShopId.forEach(product -> {
//              List<Stock> stockList = product.getStockList()
//                      .stream().filter(stock -> stock.getShop().getShopId() == shopId)
//                      .collect(Collectors.toList());
//               product.setStockList(stockList);
//          });
//        return allProductsByShopId;
//    }
    public Product createProduct1(long categoryId, Product product){
        Optional<Category> category = categoryRepository.findById(categoryId);
        category.ifPresent(categoryOptional -> categoryOptional.addProduct(product));
        category.orElseThrow(()->  new EntityNotFoundException("NOt found category"));
        var save = categoryRepository.save(category.get());
//        Optional<Product> optionalProduct = productRepository.findById(product.getProductId());
        var productOptional = save.getProductList().stream()
                .filter(product1 -> product1.getProductId() == product.getProductId()).findFirst();
        if (productOptional.isPresent()){
            return productOptional.get();
        }else {
            throw  new EntityNotFoundException("product not save id: "+product.getProductId());
        }

    }



}

package com.example.supershop.services;

import com.example.supershop.exception.EntityAlreadyExistException;
import com.example.supershop.exception.EntityNotFoundException;
import com.example.supershop.exception.StockNOtAvailabelException;
import com.example.supershop.model.Product;
import com.example.supershop.model.Shop;
import com.example.supershop.model.Stock;
import com.example.supershop.repository.ProductRepository;
import com.example.supershop.repository.ShopRepository;
import com.example.supershop.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Transactional
public class StockService {
    private final StockRepository stockRepository;
    private final ProductRepository productRepository;
    private  final ShopRepository shopRepository;

    public StockService(StockRepository stockRepository,
                        ProductRepository productRepository,
                        ShopRepository shopRepository) {
        this.stockRepository = stockRepository;
        this.productRepository = productRepository;
        this.shopRepository = shopRepository;
    }

    public Stock createStock(long shopId, long productId , Stock  stock) throws EntityNotFoundException, EntityAlreadyExistException {
        Optional<Shop> shop = shopRepository.findById(shopId);
        Optional<Product> product = productRepository.findById(productId);
        Optional<Stock> stockOptional = stockRepository.findById(stock.getStockId());

        shop.ifPresent(stock::setShop);
        shop.orElseThrow(EntityNotFoundException::new);

        product.ifPresent(stock::setProduct);
        product.orElseThrow(EntityNotFoundException::new);
        Stock saveStock = new Stock();
        if (stockOptional.isEmpty()){
            stock.setProduct(product.get());
            saveStock = stockRepository.save(stock);
         } else {
            throw  new EntityAlreadyExistException("Stock id already exist "+ stock.getStockId());
        }
        productRepository.save(product.get());
        return  saveStock;
    }

//    public Stock updateStock(long shopId, long productId,Stock stock) throws EntityNotFoundException {
//        Optional<Shop> shop = shopRepository.findById(shopId);
//        Optional<Product> product = productRepository.findById(productId);
//        Stock saveStock = new Stock();
//        AtomicLong stockId = new AtomicLong();
//        if (shop.isPresent()){
//            System.out.println("shopId = " + shopId);
//            if(product.isPresent()){
//                stockId.set(getStockId(shopId, product.get()));
//                Optional<Stock> stockOptional = stockRepository.findById(stockId.get());
//                if (stockOptional.isPresent()){
//                    saveStock = getStockUpdate(stock.getQuantity(), product, stockId, stockOptional.get());
//                }
//                stockOptional.orElseThrow(()-> new EntityNotFoundException("Stock not found: Id = "+stockId));
//                productRepository.save(product.get());
//                return  saveStock;
//            } else
//                throw new EntityNotFoundException(product+ " Not Found !");
//
//        }else {
//            throw  new EntityNotFoundException(shop+ "Not found !");
//        }
//
//    }
//    public Stock updateStock(long shopId, long productId,int quantity) throws EntityNotFoundException {
//        Optional<Shop> shop = shopRepository.findById(shopId);
//        Optional<Product> product = productRepository.findById(productId);
//        Stock saveStock = new Stock();
//        AtomicLong stockId = new AtomicLong();
//        if (shop.isPresent()){
//            System.out.println("shopId = " + shopId);
//            if(product.isPresent()){
//                stockId.set(getStockId(shopId, product.get()));
//                Optional<Stock> stockOptional = stockRepository.findById(stockId.get());
//                  if (stockOptional.isPresent()){
//                      saveStock = getStockForSale(quantity, product.get(), stockId, stockOptional.get());
//                }
//                productRepository.save(product.get());
//                return  saveStock;
//            } else
//                throw new EntityNotFoundException(product);
//
//        }else {
//            throw  new EntityNotFoundException(shop);
//        }
//
//    }

    private Stock getStockForSale(int quantity, Product product, AtomicLong stockId, Stock stock) throws StockNOtAvailabelException, EntityNotFoundException {
        Stock saveStock = new Stock();
        if (stock != null){
            stock.setProduct(product);
            int updateStockQuantity = stock.getQuantity() - quantity;
           if((stock.getQuantity()) != 0 && updateStockQuantity >=0 ){
               System.out.println("updateStock = " + updateStockQuantity);
               stock.setQuantity(updateStockQuantity);
               saveStock = stockRepository.save(stock);
//               productRepository.save(product);
           }
           else {
               throw new StockNOtAvailabelException(product.getProductName() +": ");
           }
        } else {
            throw  new EntityNotFoundException(stockId+"");
        }
        return saveStock;
    }


    private Stock getStockUpdate(int quantity, Optional<Product> product, AtomicLong stockId, Stock stockOptional) throws EntityNotFoundException {
        Stock saveStock = new Stock();
        if (stockOptional != null){
            stockOptional.setProduct(product.get());
            int updateStockQuantity = stockOptional.getQuantity() + quantity;
                System.out.println("updateStock = " + updateStockQuantity);
                stockOptional.setQuantity(updateStockQuantity);
                saveStock = stockRepository.save(stockOptional);
                productRepository.save(product.get());
        } else {
            throw  new EntityNotFoundException(stockId+"");
        }
        return saveStock;
    }

//    private long getStockId(long shopId, Product product) {
//        AtomicLong stockId = new AtomicLong();
//        product.getStockList().stream()
//                .filter(stock -> stock.getShop().getShopId() == shopId)
////                .filter(stock -> stock.getQuantity()>0)
//                .mapToLong(Stock::getStockId).forEach(stockId::set);
//        return stockId.get();
//    }


}

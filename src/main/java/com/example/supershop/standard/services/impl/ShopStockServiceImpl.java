package com.example.supershop.standard.services.impl;

import com.example.supershop.dto.respose.Response;
import com.example.supershop.exception.EntityNotFoundException;
import com.example.supershop.exception.StockNOtAvailabelException;
import com.example.supershop.model.Stock;
import com.example.supershop.repository.ProductRepository;
import com.example.supershop.repository.ShopRepository;
import com.example.supershop.repository.StockRepository;
import com.example.supershop.standard.services.ShopStockService;
import com.example.supershop.util.ResponseBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.example.supershop.util.ResponseBuilder.getFailureResponse;
import static com.example.supershop.util.ResponseBuilder.getSuccessResponse;
@Service("StockService")
public class ShopStockServiceImpl implements ShopStockService {
    private final StockRepository stockRepository;
    private final ShopRepository shopRepository;
    private final ProductRepository productRepository;

    public ShopStockServiceImpl(StockRepository stockRepository, ShopRepository shopRepository, ProductRepository productRepository) {
        this.stockRepository = stockRepository;
        this.shopRepository = shopRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Response createStock(long shopId, long productId, Stock stock) {
        var shop = shopRepository.findById(shopId);
        if (shop.isPresent()){
            stock.setShop(shop.get());
            var optionalProduct = productRepository.findByProductIdAndIsActiveTrue(productId);
            if (optionalProduct.isPresent()){
                stock.setProduct(optionalProduct.get());
                var stockOptional = stockRepository.findById(stock.getStockId());
                if (stockOptional.isEmpty()){
                    stock.setProduct(optionalProduct.get());
                    var saveStock = stockRepository.save(stock);
                    return getSuccessResponse(HttpStatus.CREATED,"Stock created",saveStock);
                }else {
                    return getFailureResponse(HttpStatus.BAD_REQUEST,
                            "Stock already exist. Id: "+stock.getStockId());
                }
            }else {
                return getFailureResponse(HttpStatus.NOT_FOUND,
                        "Product not found ! Id: "+productId);
            }
        }else {
           return getFailureResponse(HttpStatus.NOT_FOUND,
                   "Shop not found! Id: "+shopId);
        }
    }

    @Override
    public Response getStockById(long stockId) {
        var stock = stockRepository.findById(stockId);
        return stock.map(value -> ResponseBuilder.getSuccessResponse(HttpStatus.OK, "Stock found", value))
                .orElse(ResponseBuilder.getFailureResponse(HttpStatus.BAD_REQUEST,"Sock not found"));
    }

    @Override
    public Response getStockByPage(long shopId, Pageable pageable) {
        var  isExists = shopRepository.existsById(shopId);
        if (isExists){
            Page<?> page = stockRepository.findAllByShopShopId(shopId, pageable);
            return ResponseBuilder.getSuccessResponsePage(HttpStatus.OK,"Stock page",page);
        }
        return ResponseBuilder.getFailureResponse(HttpStatus.BAD_REQUEST,"Shop not found id: "+shopId);
    }

    @Override
    public Response updateStock(long shopID, long productId, Stock stock) {
        var shop = shopRepository.findById(shopID);
        if (shop.isPresent()){
            var product = productRepository.findByProductIdAndIsActiveTrue(productId);

         }
        throw new UnsupportedOperationException();
    }

    @Override
    public Stock updateStock(long shopId, long productId, int quantity) throws StockNOtAvailabelException {
        var stock = stockRepository.findByShop_ShopIdAndProduct_ProductId(shopId, productId);
        if (stock.isPresent()){
            var  quantityIsPresent = stock.get().getQuantity();
            var updateQuantity = quantityIsPresent - quantity;
            if ((quantityIsPresent != 0) && updateQuantity >=0){
                stock.get().setQuantity(updateQuantity);
                return stockRepository.save(stock.get());
            }else {
                throw  new StockNOtAvailabelException("ShopId: "+shopId+ " productId: "+productId);
            }
        }
        throw  new StockNOtAvailabelException(String.format("ShopId: %d productId: %d", shopId, productId));

    }

    @Override
    public Stock updateStock(long stockId, int quantity) {
        var stockOptional = stockRepository.findById(stockId);
        if (stockOptional.isPresent()){
            var stock = stockOptional.get();
            stock.setQuantity(stock.getQuantity() + quantity);
           return stockRepository.save(stockOptional.get());
        }
        throw new EntityNotFoundException("stock not found id: "+stockId);
    }

    @Override
    public Stock findByShopIdAndProductId(long shopId, long productId) {
        var stock = stockRepository.findByShop_ShopIdAndProduct_ProductId(shopId, productId);
        if (stock.isPresent()){
            return stock.get();
        }
        throw  new  EntityNotFoundException("stock not found shopId: "+shopId+" productId: "+productId);
    }

}

package com.example.supershop.standard.services.impl;

import com.example.supershop.dto.request.StockDto;
import com.example.supershop.dto.respose.Response;
import com.example.supershop.exception.EntityNotFoundException;
import com.example.supershop.exception.StockNOtAvailabelException;
import com.example.supershop.model.Stock;
import com.example.supershop.repository.ProductRepository;
import com.example.supershop.repository.ShopRepository;
import com.example.supershop.repository.StockRepository;
import com.example.supershop.standard.services.ShopStockService;
import com.example.supershop.util.ResponseBuilder;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.example.supershop.util.ResponseBuilder.*;

@Service("StockService")
@Log4j2
public class ShopStockServiceImpl implements ShopStockService {
    private final StockRepository stockRepository;
    private final ShopRepository shopRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ShopStockServiceImpl(StockRepository stockRepository, ShopRepository shopRepository,
                                ProductRepository productRepository, ModelMapper modelMapper) {
        this.stockRepository = stockRepository;
        this.shopRepository = shopRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Response createStock(long shopId, long productId, Stock stock) {
        var shop = shopRepository.findById(shopId);
        if (shop.isPresent()) {
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
        return stock.map(value -> getSuccessResponse(HttpStatus.OK, "Stock found", value))
                .orElse(getFailureResponse(HttpStatus.BAD_REQUEST, "Sock not found"));
    }

    @Override
    public Response getStockByPage(long shopId, Pageable pageable) {
        var  isExists = shopRepository.existsById(shopId);
        if (isExists){
            Page<?> page = stockRepository.findAllByShopShopId(shopId, pageable);
            return getSuccessResponsePage(HttpStatus.OK, "Stock page", page);
        }
        return getFailureResponse(HttpStatus.BAD_REQUEST, "Shop not found id: " + shopId);
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
        if (stock.isPresent()) {
            return stock.get();
        }
        throw new EntityNotFoundException("stock not found shopId: " + shopId + " productId: " + productId);
    }


    @Override
    public Response createStock(StockDto stockDto) {
        var stock = modelMapper.map(stockDto, Stock.class);
        var optionalStock = stockRepository
                .findByShop_ShopIdAndProduct_ProductId(stock.getShop().getShopId(),
                        stock.getProduct().getProductId());

        if (optionalStock.isEmpty()) {
            var saveStock = stockRepository.save(stock);
            return getSuccessResponse(HttpStatus.CREATED, "Stock created",
                    modelMapper.map(saveStock, StockDto.class));
        }
        return getFailureResponse(HttpStatus.NOT_ACCEPTABLE,
                "stock already exist id: " + optionalStock.get().getStockId());
    }

    @Override
    public Response updateStock(StockDto stockDto) {
        var stockOptional = stockRepository
                .findByStockIdAndIsActiveTrueAndShop_ShopIdAndProduct_ProductId(stockDto.getStockId(),
                        stockDto.getShop().getShopId(), stockDto.getProduct().getProductId());
        if (stockOptional.isPresent()) {
            var stock = modelMapper.map(stockDto, Stock.class);
            stock.setStockId(stockOptional.get().getStockId());
            var updateStock = stockRepository.save(stock);
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK, "stock updated", updateStock);
        }
        return ResponseBuilder.getFailureResponse(HttpStatus.NOT_FOUND,
                "Stock not found Id: " + stockDto.getStockId());
    }

    @Override
    public Response findAllByShopAndCategoryId(long shopId, long categoryId, Pageable pageable) {
        var page = stockRepository.findByShop_ShopIdAndProductCategoryId(shopId, categoryId, pageable)
                .map(stock -> modelMapper.map(stock, StockDto.class));
        return getSuccessResponsePage(HttpStatus.OK, " All Stock by shop and category", page);
    }

    private void mapping() {
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.createTypeMap(StockDto.class, Stock.class)
                .addMapping(StockDto::getProduct, Stock::setProduct)
                .addMapping(StockDto::getShop, Stock::setShop);
    }
}

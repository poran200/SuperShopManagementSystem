package com.example.supershop.standard.services;

import com.example.supershop.dto.request.StockDto;
import com.example.supershop.dto.respose.Response;
import com.example.supershop.exception.StockNOtAvailabelException;
import com.example.supershop.model.Stock;
import org.springframework.data.domain.Pageable;

public interface ShopStockService {
    Response  createStock(long shopId, long productId, Stock stock);
    Response  getStockById(long stockId);
    Response  getStockByPage(long shopId, Pageable pageable);
    Response  updateStock(long shopID,long productId,Stock stock);
    Stock     updateStock(long shopId,long productId,int quantity) throws StockNOtAvailabelException;
    Stock updateStock(long stockId, int quantity);

    Stock findByShopIdAndProductId(long shopId, long productId);

    Response createStock(StockDto stockDto);
}

package com.example.supershop.standard.services;

import com.example.supershop.dto.request.CreateStockRequestDto;
import com.example.supershop.dto.respose.Response;
import com.example.supershop.model.WareHouseStock;
import org.springframework.data.domain.Pageable;

public interface WarehouseStockService {
    Response  create(CreateStockRequestDto stock);
    Response  getByIdAndIsActive(long id);
    Response  getAllByWarehouseIdAndByPage(long id, Pageable pageable);
    Response  getByWarehouseIdAndByProductId(long warehouseId, long productId);
    Response  updateStock(long stockId, WareHouseStock stock);
    /**
     * @param stockId warehouse stockId
     * @return nothing
     * set is active true existing warehouse stock
     */
    Response deleteStock(long stockId);

    WareHouseStock updateStockParches(long warehouseId, long productId, int quantity);
}

package com.example.supershop.standard.services.impl;

import com.example.supershop.dto.request.CreateStockRequestDto;
import com.example.supershop.dto.respose.Response;
import com.example.supershop.model.WareHouseStock;
import com.example.supershop.repository.ProductRepository;
import com.example.supershop.repository.WareHouseRepository;
import com.example.supershop.repository.WareHouseStockRepository;
import com.example.supershop.standard.services.WarehouseStockService;
import com.example.supershop.util.ResponseBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.example.supershop.util.ResponseBuilder.*;

@Service("WarehouseStockService")
public class WarehouseStockServiceImpl implements WarehouseStockService {
    private final WareHouseStockRepository wareHouseStockRepository;
    private final WareHouseRepository wareHouseRepository;
    private final ProductRepository productRepository;

    public WarehouseStockServiceImpl(WareHouseStockRepository wareHouseStockRepository,
                                     WareHouseRepository wareHouseRepository, ProductRepository productRepository) {
        this.wareHouseStockRepository = wareHouseStockRepository;
        this.wareHouseRepository = wareHouseRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Response create(CreateStockRequestDto stock) {
        WareHouseStock wareHouseStock = new WareHouseStock();
        var warehouse = wareHouseRepository.findByWarehouseIdAndIsActiveTrue(stock.getWarehouseId());
        if (warehouse.isPresent()){
            wareHouseStock.setWareHouse(warehouse.get());
            var product = productRepository.findByProductIdAndIsActiveTrue(stock.getProductId());
            if (product.isPresent()){
                wareHouseStock.setProduct(product.get());
                wareHouseStock.setQuantity(stock.getQuantity());
              wareHouseStock =  wareHouseStockRepository.save(wareHouseStock);
              return getSuccessResponse(HttpStatus.CREATED,"Stock created",wareHouseStock);
            }else {
               getFailureResponse(HttpStatus.NOT_FOUND,"product not found Id: "+stock.getProductId());
            }
        }
        return getFailureResponse(HttpStatus.NOT_FOUND,"Warehouse not found Id: "+stock.getWarehouseId());
    }

    @Override
    public Response getByIdAndIsActive(long id) {
        return wareHouseStockRepository.findByStockIdAndIsActiveTrue(id)
                .map(stock -> getSuccessResponse(HttpStatus.OK,"stock found",stock))
                .orElse(getFailureResponse(HttpStatus.NOT_FOUND,"stock not found id: "+id));
    }

    @Override
    public Response getAllByWarehouseIdAndByPage(long id, Pageable pageable) {
        var response= wareHouseStockRepository.findAllByWareHouseWarehouseIdAndIsActiveTrue(id,pageable);
        return getSuccessResponsePage(HttpStatus.OK,"stock found by page",response);
    }

    @Override
    public Response getByWarehouseIdAndByProductId(long warehouseId, long productId) {
        var stockOptional = wareHouseStockRepository
                .findByWareHouseWarehouseIdAndProductProductIdAndIsActiveTrue(warehouseId, productId);
       return stockOptional.map(stock -> getSuccessResponse(HttpStatus.OK,"sock found",stock))
                .orElse(getFailureResponse(HttpStatus.NOT_FOUND,"Stock not found"));
    }

    @Override
    public Response updateStock(long stockId, WareHouseStock stock) {

        return null;
    }

    @Override
    public Response deleteStock(long stockId) {
        var stock = wareHouseStockRepository.findByStockIdAndIsActiveTrue(stockId);
        if (stock.isPresent()) {
            var wareHouseStock = stock.get();
            wareHouseStock.setIsActive(false);
            wareHouseStockRepository.save(wareHouseStock);
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK, "Stock delete ", null);
        }
        return ResponseBuilder.getFailureResponse(HttpStatus.NOT_FOUND, "Stock not found  Id: " + stockId);

    }

    @Override
    public WareHouseStock updateStockParches(long warehouseId, long productId, int quantity) {
        var stock = wareHouseStockRepository.findByWareHouseWarehouseIdAndProductProductIdAndIsActiveTrue(warehouseId, productId);
        if (stock.isPresent()) {
            var wareHouseStock = stock.get();
            wareHouseStock.setQuantity(wareHouseStock.getQuantity() + quantity);
            return wareHouseStockRepository.save(wareHouseStock);
        } else {
            CreateStockRequestDto dto = new CreateStockRequestDto();
            dto.setProductId(productId);
            dto.setWarehouseId(warehouseId);
            dto.setQuantity(quantity);
            return (WareHouseStock) create(dto).getContent();
        }
    }
}

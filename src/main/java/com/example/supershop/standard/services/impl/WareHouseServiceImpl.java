package com.example.supershop.standard.services.impl;

import com.example.supershop.dto.respose.Response;
import com.example.supershop.model.Address;
import com.example.supershop.model.WareHouse;
import com.example.supershop.repository.AddressRepository;
import com.example.supershop.repository.ProductRepository;
import com.example.supershop.repository.WareHouseRepository;
import com.example.supershop.standard.services.WareHouseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.supershop.util.ResponseBuilder.*;

@Service("WareHouseService")
@Transactional
public class WareHouseServiceImpl implements WareHouseService {
    private final WareHouseRepository wareHouseRepository;
    private final ProductRepository productRepository;
    private final AddressRepository addressRepository;

    public WareHouseServiceImpl(WareHouseRepository wareHouseRepository, ProductRepository productRepository, AddressRepository addressRepository) {
        this.wareHouseRepository = wareHouseRepository;
        this.productRepository = productRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public Response create(WareHouse wareHouse) {
        var optionalWareHouse = wareHouseRepository.findByWarehouseIdAndIsActiveTrue(wareHouse.getWarehouseId());
        if (optionalWareHouse.isEmpty()){
            var address = addressRepository.save(wareHouse.getAddress());
            wareHouse.setAddress(address);
            var saveWarehouse = wareHouseRepository.save(wareHouse);
            return getSuccessResponse(HttpStatus.CREATED,"ware house created",saveWarehouse);
        }
        return getFailureResponse(HttpStatus.NOT_FOUND,"Entity already exists Id:  "+ wareHouse.getWarehouseId());
    }

    @Override
    public Response getAllAndIsActiveTrue(Pageable pageable) {
        Page<?> page = wareHouseRepository.findAllByIsActiveTrue(pageable);
        return  getSuccessResponsePage(HttpStatus.OK,"Warehouse list ",page);
    }

    @Override
    public Response getById(long id) {
        var wareHouse = wareHouseRepository.findByWarehouseIdAndIsActiveTrue(id);
        if (wareHouse.isPresent()){
            return getSuccessResponse(HttpStatus.OK,"warehouse found",wareHouse);
        }
        return getFailureResponse(HttpStatus.NOT_FOUND,"Not found ! Id: "+id);
    }

    @Override
    public Response getByName(String name) {
        var wareHouse = wareHouseRepository.findByName(name);
        if (wareHouse.isPresent()){
            return getSuccessResponse(HttpStatus.OK,"warehouse found ",wareHouse);
        }
        return getFailureResponse(HttpStatus.NOT_FOUND,"Not found");
    }

    @Override
    public Response getByIdAndIsActiveTrue(long id) {
        var wareHouse = wareHouseRepository.findByWarehouseIdAndIsActiveTrue(id);
      return   wareHouse.map( wareHouse1 -> getSuccessResponse(HttpStatus.OK,"warehouse found ",wareHouse1))
                .orElse(getFailureResponse(HttpStatus.NOT_FOUND,"warehouse not found Id: "+id));
     }

    @Override
    public Response addProductInWareHouse(long wareHouseId, long productId) {
        var wareHouseOptional = wareHouseRepository.findByWarehouseIdAndIsActiveTrue(wareHouseId);
        if(wareHouseOptional.isPresent()){
            var product = productRepository.findByProductIdAndIsActiveTrue(productId);
            if (product.isPresent()){
                wareHouseOptional.get().addProduct(product.get());
                var wareHouse = wareHouseRepository.save(wareHouseOptional.get());
                return getSuccessResponse(HttpStatus.OK,"Warehouse found",wareHouse);
            }else {
                return getFailureResponse(HttpStatus.NOT_FOUND,"product not found Id: "+productId);
            }
        }
        return getFailureResponse(HttpStatus.NOT_FOUND,"warehouse not found id: "+wareHouseId);
    }
}

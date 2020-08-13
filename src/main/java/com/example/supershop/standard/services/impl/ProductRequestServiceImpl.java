package com.example.supershop.standard.services.impl;

import com.example.supershop.dto.request.ProductRequestDto;
import com.example.supershop.dto.respose.Response;
import com.example.supershop.model.ProductRequest;
import com.example.supershop.model.RequestLineItem;
import com.example.supershop.repository.ProductRequestRepository;
import com.example.supershop.repository.RequestLineItemRepository;
import com.example.supershop.standard.services.ProductRequestService;
import com.example.supershop.util.ResponseBuilder;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.supershop.util.ResponseBuilder.*;

@Log4j2
@Service("ProductRequestService")
@Transactional
public class ProductRequestServiceImpl implements ProductRequestService {
    private final ProductRequestRepository productRequestRepository;
    private final ModelMapper modelMapper;
    private final RequestLineItemRepository lineItemRepository;

    public ProductRequestServiceImpl(ProductRequestRepository productRequestRepository,
                                     ModelMapper modelMapper, RequestLineItemRepository lineItemRepository) {
        this.productRequestRepository = productRequestRepository;
        this.modelMapper = modelMapper;
        this.lineItemRepository = lineItemRepository;
    }

    @Override
    public Response create(ProductRequestDto productRequestDto, RequestLineItem... lineItems) {

//        ProductRequest productRequest = new ProductRequest();
        var productRequest = modelMapper.map(productRequestDto, ProductRequest.class);
        productRequest.addItem(productRequestDto.getRequestLineItems());
        var save = productRequestRepository.save(productRequest);
        return ResponseBuilder.getSuccessResponse(HttpStatus.CREATED, "created", modelMapper.map(save, ProductRequestDto.class));
    }

    @Override
    public Response findById(long id) {
        var productRequest = productRequestRepository.findByAndRequestIdAndIsActiveTrue(id);
        return productRequest.map(request -> getSuccessResponse(HttpStatus.OK, "product request",
                modelMapper.map(request, ProductRequestDto.class)))
                .orElse(getFailureResponse(HttpStatus.NOT_FOUND, "request not found"));
    }

    @Override
    public Response findAllByPage(Pageable pageable) {
        var page = productRequestRepository.findAll(pageable);
        return getSuccessResponsePage(HttpStatus.OK, "page", page);
    }

    @Override
    public Response findByShopId(long shopId, Pageable pageable) {
        var requests = productRequestRepository.findAllByIsActiveTrueAndRequestShopShopId(shopId, pageable);
        return getSuccessResponsePage(HttpStatus.OK, "shop request page", requests);
    }

    @Override
    public Response findByWarehouseId(long warehouseId, Pageable pageable) {
        var productRequests = productRequestRepository.findAllByIsActiveTrueAndWareHouseWarehouseId(warehouseId, pageable);
        return getSuccessResponsePage(HttpStatus.OK, "page found", productRequests);
    }

    @Override
    public Response update(long id, ProductRequest editRequest) {
        var request = productRequestRepository.findByAndRequestIdAndIsActiveTrue(id);
        if (request.isPresent()) {
            editRequest.setRequestId(request.get().getRequestId());
            editRequest.addItem(editRequest.getRequestLineItems());
            var save = productRequestRepository.save(editRequest);
            return getSuccessResponse(HttpStatus.OK, "Updated request", save);
        }
        return getFailureResponse(HttpStatus.NOT_FOUND, "not found");
    }

    @Override
    public Response delete(long id) {
        var request = productRequestRepository.findByAndRequestIdAndIsActiveTrue(id);
        if (request.isPresent()) {
            request.get().setIsActive(false);
            productRequestRepository.save(request.get());
            return getSuccessResponse(HttpStatus.OK, "deleted susscefully", null);
        }
        return getFailureResponse(HttpStatus.NOT_FOUND, "not found Id: " + id);
    }

}

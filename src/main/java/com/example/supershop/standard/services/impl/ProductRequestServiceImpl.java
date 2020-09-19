package com.example.supershop.standard.services.impl;

import com.example.supershop.controller.ProductRequestController;
import com.example.supershop.controller.ShopController;
import com.example.supershop.controller.WarehouseController;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static com.example.supershop.util.ResponseBuilder.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
        return ResponseBuilder.getSuccessResponse(HttpStatus.CREATED, "created", productRequestDto);
    }

    @Override
    @Transactional
    public Response findById(long id) {
        var productRequest = productRequestRepository.findByAndRequestIdAndIsActiveTrue(id);
//                .map(productRequest1 -> modelMapper.map(productRequest1,ProductRequestDto.class));
        var requestDto = modelMapper.map(productRequest, ProductRequestDto.class);
        return productRequest.map(request -> getSuccessResponse(HttpStatus.OK, "product request",
                addLink(requestDto, productRequest.get().getRequestShop().getShopId(),
                        productRequest.get().getWareHouse().getWarehouseId())))
                .orElse(getFailureResponse(HttpStatus.NOT_FOUND, "request not found"));
    }

    @Override
    public Response findAllByPage(Pageable pageable) {
        var page = productRequestRepository.findAll(pageable);
        return getSuccessResponsePage(HttpStatus.OK, "page",
                getpagewithLink(page, page.getPageable()));
    }


    @Override
    public Response findByShopId(long shopId, Pageable pageable) {
        var requests = productRequestRepository.findAllByIsActiveTrueAndRequestShopShopId(shopId, pageable);
        return getSuccessResponsePage(HttpStatus.OK, "shop request page",
                getpagewithLink(requests, requests.getPageable()));
    }


    @Override
    public Response findByWarehouseId(long warehouseId, Pageable pageable) {
        var productRequests = productRequestRepository
                .findAllByIsActiveTrueAndWareHouseWarehouseId(warehouseId, pageable);
        return getSuccessResponsePage(HttpStatus.OK, "page found",
                getpagewithLink(productRequests, productRequests.getPageable()));
    }

    @Override
    public Response update(long id, ProductRequest editRequest) {
        var request = productRequestRepository.findByAndRequestIdAndIsActiveTrue(id);
        if (request.isPresent()) {
            editRequest.setRequestId(request.get().getRequestId());
            editRequest.addItem(editRequest.getRequestLineItems());
            var save = productRequestRepository.save(editRequest);
            var dto = modelMapper.map(save, ProductRequestDto.class);
            return getSuccessResponse(HttpStatus.OK, "Updated request",
                    addLink(dto, save.getRequestShop().getShopId(), save.getWareHouse().getWarehouseId()));
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

    /**
     * add shop link
     * add warehouse link
     */
    private ProductRequestDto addLink(ProductRequestDto request, long shopId, long warehouseId) {
        var requestDto = modelMapper.map(request, ProductRequestDto.class);
        requestDto.add(linkTo(methodOn(ProductRequestController.class).findById(request.getRequestId())).withSelfRel());
        requestDto.add(linkTo(methodOn(ShopController.class).getBYId(shopId)).withRel("shop"));
        requestDto.add(linkTo(methodOn(WarehouseController.class)
                .findById(warehouseId)).withRel("wareHouse"));
        return requestDto;
    }

    private Page<ProductRequestDto> getpagewithLink(Page<ProductRequest> page, Pageable pageable) {
        var dtoList = new ArrayList<ProductRequestDto>();
        for (ProductRequest productRequest : page.getContent()) {
            var productRequestDto = addLink(modelMapper.map(productRequest, ProductRequestDto.class),
                    productRequest.getRequestShop().getShopId(),
                    productRequest.getWareHouse().getWarehouseId());
            dtoList.add(productRequestDto);
        }
        return new PageImpl<>(dtoList, pageable, dtoList.size());
    }


}

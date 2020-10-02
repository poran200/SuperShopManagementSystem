package com.example.supershop.standard.services.impl;

import com.example.supershop.controller.PurchaseInvoiceController;
import com.example.supershop.controller.WarehouseController;
import com.example.supershop.dto.request.PurchaseInvoiceRequest;
import com.example.supershop.dto.respose.ParchedInvoiceResponseDto;
import com.example.supershop.dto.respose.Response;
import com.example.supershop.enam.StockUpdateStatus;
import com.example.supershop.exception.EntityNotFoundException;
import com.example.supershop.model.*;
import com.example.supershop.repository.*;
import com.example.supershop.standard.services.ParchesService;
import com.example.supershop.standard.services.WarehouseStockService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.supershop.util.ResponseBuilder.getFailureResponse;
import static com.example.supershop.util.ResponseBuilder.getSuccessResponse;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.*;

@Service
public class ParchesServiceImpl implements ParchesService {
    private final ParchedInvoiceRepository invoiceRepository;
    private final ParchedInvoiceLineItemRepository lineItemRepository;
    private final UserRepository userRepository;
    private final WareHouseRepository wareHouseRepository;
    private final ProviderRepository providerRepository;
    private final WarehouseStockService warehouseStockService;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ParchesServiceImpl(ParchedInvoiceRepository invoiceRepository, ParchedInvoiceLineItemRepository lineItemRepository, UserRepository userRepository, WareHouseRepository wareHouseRepository, ProviderRepository providerRepository, WarehouseStockService warehouseStockService, ProductRepository productRepository, ModelMapper modelMapper) {
        this.invoiceRepository = invoiceRepository;
        this.lineItemRepository = lineItemRepository;
        this.userRepository = userRepository;
        this.wareHouseRepository = wareHouseRepository;
        this.providerRepository = providerRepository;
        this.warehouseStockService = warehouseStockService;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Response crateParchesInvoice(PurchaseInvoiceRequest invoiceRequest) {
        ParchedInvoice parchedInvoice = new ParchedInvoice();
        List<ParchedInvoiceLineItem> items = new ArrayList<>();
        if (isItemsEmpty(invoiceRequest))
            return getFailureResponse(NOT_ACCEPTABLE, "Product list must have one Item ");
        var optionalUser = userRepository.findById(invoiceRequest.getUserId());
        // todo
        //update security context and set user
        if (optionalUser.isPresent()) {
            parchedInvoice.setUser(optionalUser.get());
            var wareHouse = wareHouseRepository.findByWarehouseIdAndIsActiveTrue(invoiceRequest.getWarehouseId());
            if (wareHouse.isPresent()) {
                parchedInvoice.setWareHouse(wareHouse.get());
                var provider = providerRepository.findByIdAndIsActiveTrue(invoiceRequest.getProviderId());
                if (provider.isPresent()) {
                    parchedInvoice.setProvider(provider.get());
                } else {
                    return getFailureResponse(NOT_FOUND, "Provider not found Id: " + invoiceRequest.getProviderId());
                }
            } else {
                return getFailureResponse(NOT_FOUND, "Warehouse Not found ID: " + invoiceRequest.getWarehouseId());
            }

        } else {
            return getFailureResponse(NOT_FOUND, "User not found ");
        }
        try {
            invoiceRequest.getItemLineRequests().forEach(itemLineRequest -> {
                var productOptional = productRepository.findByProductIdAndIsActiveTrue(itemLineRequest.getProductId());
                productOptional.ifPresent(product -> parchedInvoice
                        .add(new ParchedInvoiceLineItem(product, itemLineRequest.getQuantity())));
                productOptional.orElseThrow(() -> new EntityNotFoundException("product not found Id: " + itemLineRequest.getProductId()));
                warehouseStockService.updateStockParches(invoiceRequest.getWarehouseId(),
                        itemLineRequest.getProductId(), itemLineRequest.getQuantity(), StockUpdateStatus.INCREASE);
            });
        } catch (Exception e) {

            return getFailureResponse(BAD_REQUEST, e.getMessage());
        }

        var invoiceCalculateTotalAndVat = calculateTotalAndVat(parchedInvoice, parchedInvoice.getInvoiceItems());
        var saveInvoice = invoiceRepository.save(invoiceCalculateTotalAndVat);
//        lineItemRepository.saveAll(parchedInvoice.getInvoiceItems());
        return getSuccessResponse(OK, "Parches invoice created",
                linkAdd(modelMapper.map(saveInvoice, ParchedInvoiceResponseDto.class)));

    }

    private boolean isItemsEmpty(PurchaseInvoiceRequest invoiceRequest) {
        return invoiceRequest.getItemLineRequests().isEmpty();
    }

    @Override
    public Response getById(Long invoiceId) {
        return null;
    }

    @Override
    public Response updateParchesInvoice(Long invoiceId, ParchedInvoice invoice) {
        return null;
    }

    private ParchedInvoice convertRequest(PurchaseInvoiceRequest invoiceRequest) {
        var user = new User();
        var provider = new Provider();
        var wareHouse = new WareHouse();
        user.setUserId(invoiceRequest.getUserId());
        provider.setId(invoiceRequest.getUserId());
        wareHouse.setWarehouseId(invoiceRequest.getWarehouseId());
        //TODO
        return null;
    }

    private ParchedInvoiceResponseDto linkAdd(ParchedInvoiceResponseDto dto) {
        dto.add(linkTo(methodOn(PurchaseInvoiceController.class).findById(dto.getId())).withSelfRel());
        dto.add(linkTo(methodOn(WarehouseController.class).findById(dto.getWareHouse().getWarehouseId())).withRel("warehouse"));
        return dto;
    }

    private ParchedInvoice calculateTotalAndVat(ParchedInvoice parchedInvoice, List<ParchedInvoiceLineItem> lineItems) {
        parchedInvoice.setTotalBill(lineItems.stream().mapToDouble(ParchedInvoiceLineItem::getCalculatePrice).sum());
        parchedInvoice.setTotalVat(lineItems.stream().mapToDouble(ParchedInvoiceLineItem::getTotalVat).sum());
        parchedInvoice.setInvoiceItems(lineItems);
        return parchedInvoice;
    }
}

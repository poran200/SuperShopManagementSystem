package com.example.supershop.standard.services.impl;

import com.example.supershop.controller.PurchaseInvoiceController;
import com.example.supershop.controller.WarehouseController;
import com.example.supershop.dto.request.PurchaseInvoiceRequest;
import com.example.supershop.dto.respose.ParchedInvoiceResponseDto;
import com.example.supershop.dto.respose.Response;
import com.example.supershop.enam.StockUpdateStatus;
import com.example.supershop.model.ParchedInvoice;
import com.example.supershop.model.ParchedInvoiceLineItem;
import com.example.supershop.repository.ParchedInvoiceLineItemRepository;
import com.example.supershop.repository.ParchedInvoiceRepository;
import com.example.supershop.standard.services.PurchaseInvoiceService;
import com.example.supershop.standard.services.WarehouseStockService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.example.supershop.util.ResponseBuilder.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service("PurchaseInvoiceService")
@Transactional
@Log4j2
public class PurchaseInvoiceServiceImpl implements PurchaseInvoiceService {
    private final ParchedInvoiceRepository invoiceRepository;
    private final ModelMapper modelMapper;
    private final ParchedInvoiceLineItemRepository invoiceLineItemRepository;
    private final WarehouseStockService warehouseStockService;

    public PurchaseInvoiceServiceImpl(ParchedInvoiceRepository invoiceRepository, ModelMapper modelMapper, ParchedInvoiceLineItemRepository invoiceLineItemRepository, WarehouseStockService warehouseStockService) {
        this.invoiceRepository = invoiceRepository;
        this.modelMapper = modelMapper;
        this.invoiceLineItemRepository = invoiceLineItemRepository;
        this.warehouseStockService = warehouseStockService;
    }

    @Override
    public Response create(PurchaseInvoiceRequest invoiceRequest) {
        ParchedInvoice invoice = new ParchedInvoice();
        List<ParchedInvoiceLineItem> lineItems = new ArrayList<>();
//        Todo
        return null;
    }

    @Override
    public Response getById(long invoiceId) {
        var invoiceOptional = invoiceRepository.findByIdAndIsActiveTrue(invoiceId);
        return invoiceOptional.map(invoice -> getSuccessResponse(HttpStatus.OK, "invoice found",
                linkAdd(modelMapper.map(invoice, ParchedInvoiceResponseDto.class))))
                .orElse(getFailureResponse(HttpStatus.NOT_FOUND, "Invoice not found Id: " + invoiceId));
    }

    @Override
    public Response getAllByPage(Pageable pageable) {
        var invoicePage = invoiceRepository.findAllByIsActiveTrue(pageable)
                .map(invoice -> modelMapper.map(invoice, ParchedInvoiceResponseDto.class));
        return getSuccessResponse(HttpStatus.OK, "Invoice page", invoicePage);
    }

    @Override
    public Response delete(long invoiceId) {
        var invoiceOptional = invoiceRepository.findByIdAndIsActiveTrue(invoiceId);
        if (invoiceOptional.isPresent()) {
            var parchedInvoice = invoiceOptional.get();
            parchedInvoice.setIsActive(false);
            invoiceRepository.save(parchedInvoice);
            return getSuccessResponse(HttpStatus.OK, "invoice deleted", null);
        }
        return getFailureResponse(HttpStatus.NOT_FOUND, "invoice not found Id: " + invoiceId);
    }

    @Override
    public Response findAllByWarehouseId(long warehouseId, Pageable pageable) {
        var invoicePage = invoiceRepository.findAllByWareHouseWarehouseIdAndIsActiveTrue(warehouseId, pageable)
                .map(invoice -> modelMapper.map(invoice, ParchedInvoiceResponseDto.class));
        invoicePage.getContent().forEach(this::linkAdd);

        return getSuccessResponsePage(HttpStatus.OK, "Invoices by warehouse", invoicePage);
    }

    @Override
    public Response deleteInvoiceLineItem(long invoiceId, long lineItemId) {
        var invoiceOptional = invoiceRepository.findByIdAndIsActiveTrue(invoiceId);
        if (invoiceOptional.isPresent()) {
            var parchedInvoice = invoiceOptional.get();
            var item = parchedInvoice.getInvoiceItems().stream()
                    .filter(lineItem -> lineItem
                            .getParchedItemId() == lineItemId)
                    .findFirst();
            if (item.isPresent()) {
                parchedInvoice.remove(item.get());
                var warehouseId = invoiceOptional.get().getWareHouse().getWarehouseId();
                var productId = item.get().getProduct().getProductId();
                warehouseStockService.updateStockParches(warehouseId, productId, item.get().getParchedItemQuantity(), StockUpdateStatus.DECREASE);
            } else {
                return getFailureResponse(HttpStatus.NOT_FOUND, "Invoice line item id not found" + lineItemId);
            }
            var invoice = calculateTotalAndVat(parchedInvoice);
            var save = invoiceRepository.save(invoice);
            return getSuccessResponse(HttpStatus.OK, "Invoice Line item deleted",
                    linkAdd(modelMapper.map(save, ParchedInvoiceResponseDto.class)));


        } else {
            return getFailureResponse(HttpStatus.NOT_FOUND, "Invoice not found Id: " + invoiceId);
        }
    }


    private ParchedInvoice calculateTotalAndVat(ParchedInvoice parchedInvoice) {
        parchedInvoice.setTotalBill(parchedInvoice
                .getInvoiceItems()
                .stream()
                .mapToDouble(ParchedInvoiceLineItem::getCalculatePrice)
                .sum());
        parchedInvoice.setTotalVat(parchedInvoice.getInvoiceItems()
                .stream()
                .mapToDouble(ParchedInvoiceLineItem::getTotalVat)
                .sum());
        return parchedInvoice;
    }

    private ParchedInvoiceResponseDto linkAdd(ParchedInvoiceResponseDto dto) {
        dto.add(linkTo(methodOn(PurchaseInvoiceController.class).findById(dto.getId())).withSelfRel());
        dto.add(linkTo(methodOn(WarehouseController.class).findById(dto.getWareHouse().getWarehouseId())).withRel("warehouse"));
        return dto;
    }


}

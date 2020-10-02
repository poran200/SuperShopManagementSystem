package com.example.supershop.standard.services.impl;

import com.example.supershop.dto.request.SaleInvoiceUpdateRequestDto;
import com.example.supershop.dto.respose.Response;
import com.example.supershop.model.SaleInvoiceLineItem;
import com.example.supershop.model.SalesInvoice;
import com.example.supershop.repository.SaleInvoiceLineItemRepository;
import com.example.supershop.repository.SaleInvoiceRepository;
import com.example.supershop.standard.services.ProductSaleService;
import com.example.supershop.standard.services.SaleInvoiceService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.supershop.util.ResponseBuilder.*;
import static org.springframework.http.HttpStatus.*;

@Service("SaleInvoiceService")
public class SaleInvoiceServiceImpl implements SaleInvoiceService {
    private final SaleInvoiceRepository saleInvoiceRepository;
    private final SaleInvoiceLineItemRepository lineItemRepository;
    private final ProductSaleService productSaleService;

    public SaleInvoiceServiceImpl(SaleInvoiceRepository saleInvoiceRepository, SaleInvoiceLineItemRepository lineItemRepository, ProductSaleService productSaleService) {
        this.saleInvoiceRepository = saleInvoiceRepository;
        this.lineItemRepository = lineItemRepository;
        this.productSaleService = productSaleService;
    }

    @Override
    public Response getById(int invoiceId) {
        var invoice = saleInvoiceRepository.findBySaleInvoiceIdAndIsActiveTrue(invoiceId);
        return invoice.map(salesInvoice -> getSuccessResponse(OK, "invoice found", salesInvoice))
                .orElse(getFailureResponse(NOT_FOUND, "Invoice not found"));
    }

    @Override
    public Response getAllByPage(Pageable pageable) {
        return getSuccessResponsePage(OK, "InvoiceList ",
                saleInvoiceRepository.findAll(pageable));
    }

    // todo
    //  temporary fix the update method
    @Override
    @Transactional
    public Response update(int invoiceId, SaleInvoiceUpdateRequestDto salesInvoice) {
        var invoiceOptional = saleInvoiceRepository.findById(invoiceId);
        SalesInvoice salesInvoiceUpdate;
        if (invoiceOptional.isPresent()) {
            salesInvoiceUpdate = invoiceOptional.get();
            salesInvoiceUpdate.setSaleInvoiceId(salesInvoiceUpdate.getSaleInvoiceId());
            salesInvoice.getItemLineRequests().forEach(itemLineUpdateRequest -> salesInvoiceUpdate.getInvoiceItems().forEach(lineItem -> {
                if (itemLineUpdateRequest.getItemLineId() == lineItem.getId()) {
                    lineItem.setItemQuantity(itemLineUpdateRequest.getQuantity());
                    lineItem.setTotalItemLinePrice(lineItem.getCalculatePrice());
                }
            }));
            var sum = salesInvoiceUpdate.getInvoiceItems().stream().mapToDouble(SaleInvoiceLineItem::getCalculatePrice).sum();
            var totalVat = salesInvoiceUpdate.getInvoiceItems().stream().mapToDouble(SaleInvoiceLineItem::getTotalVat).sum();
            salesInvoiceUpdate.setTotalBill(sum);
            salesInvoiceUpdate.setTotalVat(totalVat);
            var saveInvoice = saleInvoiceRepository.save(salesInvoiceUpdate);
            return getSuccessResponse(OK, "Invoice Updated", saveInvoice);
        }
        return getFailureResponse(NOT_FOUND,"Invoice not found");
    }

    @Override
    public Response delete(int invoiceId) {
        return null;
    }

    @Override
    public Response findAllByShopId(long shopId) {
        var invoiceList = saleInvoiceRepository.findAllByShop_ShopId(shopId);
        return getSuccessResponse(OK,"InvoiceList",invoiceList);
    }

    @Override
    public Response deleteInvoiceLineItem(int invoiceId, long lineItemId) {
        var invoiceOptional = saleInvoiceRepository.findById(invoiceId);
        if (invoiceOptional.isPresent()){
            var invoiceItems = invoiceOptional.get().getInvoiceItems();
            var invoiceLineItem = invoiceItems.stream()
                    .filter(lineItem -> lineItem.getId() == lineItemId)
                    .findFirst();
            if (invoiceLineItem.isPresent()){
                var salesInvoice = invoiceOptional.get();
                if (invoiceItems.size()>1){
                    salesInvoice.remove(invoiceLineItem.get());
                }else {
                    return getFailureResponse(NOT_ACCEPTABLE,"Invoice line item delete not possible at last one item mandatory");
                }

                var calculateInvoice = productSaleService.calculate(salesInvoice, invoiceItems);
                var invoice = saleInvoiceRepository.save(calculateInvoice);
                return getSuccessResponse(OK,"line item deleted",invoice);
            } else {
                return getFailureResponse(NOT_FOUND,"Line item not fond in Invoice Id:"+invoiceId);

            }
        }
        return getFailureResponse(NOT_FOUND,"Line item not fond in Invoice Id:"+invoiceId);
    }

    @Override
    public Response deleteInvoice(int invoiceId){
        var invoice = saleInvoiceRepository.findById(invoiceId);
        if (invoice.isPresent()){
            invoice.get().setIsActive(false);
            saleInvoiceRepository.save(invoice.get());
            return getSuccessResponse(OK,"Invoice deleted susscefully Id: "+invoiceId,null);
        }else {
            return getFailureResponse(NOT_FOUND,"invoice not found");
        }

    }

}

package com.example.supershop.standard.services;

import com.example.supershop.dto.request.PurchaseInvoiceRequest;
import com.example.supershop.dto.respose.Response;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface PurchaseInvoiceService {
    Response create(PurchaseInvoiceRequest invoiceRequest);

    Response getById(long invoiceId);

    Response getAllByPage(Pageable pageable);

    //    Response update(int invoiceId, SaleInvoiceUpdateRequestDto invoiceUpdateRequestDto);
    Response delete(long invoiceId);

    Response findAllByWarehouseId(long warehouseId, Pageable pageable);

    Response deleteInvoiceLineItem(long invoiceId, long lineItemId);


}

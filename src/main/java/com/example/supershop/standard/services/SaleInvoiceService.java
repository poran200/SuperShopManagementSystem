package com.example.supershop.standard.services;

import com.example.supershop.dto.request.SaleInvoiceUpdateRequestDto;
import com.example.supershop.dto.respose.Response;
import org.springframework.data.domain.Pageable;


public interface SaleInvoiceService {
    Response getById(int  invoiceId );
    Response getAllByPage(Pageable pageable);
    Response update(int invoiceId, SaleInvoiceUpdateRequestDto invoiceUpdateRequestDto);
    Response delete(int invoiceId);
    Response findAllByShopId(long shopId);
    Response deleteInvoiceLineItem(int invoiceId,long lineItemId);
    Response deleteInvoice(int invoiceId);
}

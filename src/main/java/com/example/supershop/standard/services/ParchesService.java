package com.example.supershop.standard.services;

import com.example.supershop.dto.request.PurchaseInvoiceRequest;
import com.example.supershop.dto.respose.Response;
import com.example.supershop.model.ParchedInvoice;


public interface ParchesService {
    Response crateParchesInvoice(PurchaseInvoiceRequest invoiceRequest);
    Response getById(Long invoiceId);
    Response updateParchesInvoice(Long invoiceId, ParchedInvoice invoice);
}

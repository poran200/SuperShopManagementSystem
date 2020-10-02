package com.example.supershop.standard.services;

import com.example.supershop.dto.request.SalesInvoiceRequest;
import com.example.supershop.dto.respose.Response;
import com.example.supershop.model.SaleInvoiceLineItem;
import com.example.supershop.model.SalesInvoice;

import java.awt.print.Pageable;
import java.util.List;

public interface ProductSaleService {

    Response  createSaleInvoice(SalesInvoiceRequest invoiceRequest);
    Response  getInvoiceById(int invoiceId);
    Response  getInvoiceByPage(Pageable pageable);

    Response updateInvoice(Long invoiceId, SalesInvoice invoice);

    SalesInvoice calculate(SalesInvoice salesInvoice, List<SaleInvoiceLineItem> lineItems);

}


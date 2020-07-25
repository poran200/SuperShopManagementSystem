package com.example.supershop.standard.services;

import com.example.supershop.dto.request.CreateProviderRequest;
import com.example.supershop.dto.respose.Response;
import com.example.supershop.model.ParchedInvoice;
import org.springframework.data.domain.Pageable;

public interface ProviderService {

    Response createProvider(CreateProviderRequest provider);
    Response getById(Long providerId);
    Response getAllProvider(Pageable pageable);
    Response updateProvider(Long providerId,CreateProviderRequest provider);
    Response deleteProvider(Long providerId);
    Response getProviderInvoices(Long providerId);
    Response updateProviderInvoice(Long providerId, ParchedInvoice invoice);

}

package com.example.supershop.controller;

import com.example.supershop.anotation.APiController;
import com.example.supershop.anotation.DataValidation;
import com.example.supershop.dto.request.CreateProviderRequest;
import com.example.supershop.dto.respose.ProviderResponseDto;
import com.example.supershop.dto.respose.Response;
import com.example.supershop.model.Provider;
import com.example.supershop.standard.services.ProviderService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

import static com.example.supershop.util.UrlConstrains.ProviderManagement.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@APiController
@RequestMapping(ROOT)
public class ProviderController {
    private final ProviderService providerService;
    private final  ModelMapper modelMapper;
    public ProviderController(ProviderService providerService, ModelMapper modelMapper) {
        this.providerService = providerService;
        this.modelMapper = modelMapper;
    }

    @PostMapping(CREATE)
    @DataValidation
    public ResponseEntity<Object> createProvider(@RequestBody CreateProviderRequest request, BindingResult bindingResult){
        Response response = providerService.createProvider(request);
         return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }

    @GetMapping(FIND_BY_ID)
    @Async
    public CompletableFuture<ResponseEntity<Object>> getProviderById(@PathVariable Long providerId) {
        var response = providerService.getById(providerId);
        if (response.getContent() != null) {
            Provider provider = (Provider) response.getContent();
            ProviderResponseDto providerResponseDto = modelMapper.map(provider, ProviderResponseDto.class);
            providerResponseDto.add(linkTo(methodOn(AddressController.class)
                    .getById(provider.getAddress().getId())).withRel("/address"));

            providerResponseDto.add(linkTo(methodOn(ProviderController.class)
                    .getProviderInvoices(providerId)).withRel(Provider_Invoices));

            response.setContent(providerResponseDto);
            return CompletableFuture.completedFuture(ResponseEntity.status((int) response.getStatusCode()).body(response));
        }
        return CompletableFuture.completedFuture(ResponseEntity.status((int) response.getStatusCode()).body(response));
    }
    @GetMapping(Provider_Invoices)
    @Async
    public CompletableFuture<ResponseEntity<Object>> getProviderInvoices(@PathVariable Long providerId){
        var response = providerService.getProviderInvoices(providerId);
        return CompletableFuture.completedFuture(ResponseEntity.status((int) response.getStatusCode()).body(response));
    }
}

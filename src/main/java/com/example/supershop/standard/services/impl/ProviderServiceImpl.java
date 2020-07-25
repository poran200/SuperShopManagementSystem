package com.example.supershop.standard.services.impl;

import com.example.supershop.dto.request.CreateProviderRequest;
import com.example.supershop.dto.respose.ProviderResponseDto;
import com.example.supershop.dto.respose.Response;
import com.example.supershop.model.Address;
import com.example.supershop.model.ParchedInvoice;
import com.example.supershop.model.Provider;
import com.example.supershop.repository.AddressRepository;
import com.example.supershop.repository.ProviderRepository;
import com.example.supershop.standard.services.ProviderService;
import com.example.supershop.util.ResponseBuilder;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("ProviderService")
public class ProviderServiceImpl implements ProviderService {
    private final ModelMapper modelMapper;
    private final ProviderRepository providerRepository;
    private final AddressRepository addressRepository;
    public ProviderServiceImpl(ModelMapper modelMapper, ProviderRepository providerRepository, AddressRepository addressRepository) {
        this.modelMapper = modelMapper;
        this.providerRepository = providerRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    @Transactional
    public Response createProvider(CreateProviderRequest providerRequest) {
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        Provider provider = modelMapper.map(providerRequest,Provider.class);
        modelMapper.map(providerRequest,Provider.class);
        Address address = modelMapper.map(provider.getAddress(), Address.class);
        Address saveAgrees = addressRepository.save(address);
        if (saveAgrees == null){
            return ResponseBuilder.getFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR,"Internal server error when address save");
        } else {
            provider.setAddress(saveAgrees);
            Provider saveProvider = providerRepository.save(provider);
            var respondDot = modelMapper.map(saveProvider, ProviderResponseDto.class);
            if (saveProvider != null){
               return ResponseBuilder.getSuccessResponse(HttpStatus.CREATED,"provider created",respondDot);
            }
            return ResponseBuilder.getFailureResponse(HttpStatus.BAD_REQUEST,"provider not save");
        }

    }

    @Override
    @Transactional(readOnly = true)
    public Response getById(Long providerId) {
        var optionalProvider = providerRepository.findById(providerId);
        if (optionalProvider.isPresent()){
            Provider provider = optionalProvider.get();
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK,"provider found ",provider);
        }
        return ResponseBuilder.getFailureResponse(HttpStatus.NOT_FOUND,"provider not found Id: "+providerId);
    }

    @Override
    @Transactional(readOnly = true)
    public Response getAllProvider(Pageable pageable) {
        var providers = providerRepository.findAll(pageable).map(provider -> modelMapper.map(provider, ProviderResponseDto.class));
        if (providers.hasContent()){
            return ResponseBuilder.getFailureResponse(HttpStatus.NOT_FOUND,"Providers not found");
        }
        return ResponseBuilder.getSuccessResponsePage(HttpStatus.OK,"Provider found",providers);
    }

    @Override
    @Transactional
    public Response updateProvider(Long providerId, CreateProviderRequest providerRequest) {
        var optionalProvider = providerRepository.findByIdAndIsActiveTrue(providerId);
        if (optionalProvider.isPresent()){
            var provider = modelMapper.map(providerRequest, Provider.class);
            provider.setId(optionalProvider.get().getId());
            provider.setInvoiceList(optionalProvider.get().getInvoiceList());
            var saveProvider = providerRepository.save(provider);
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK,
                    "Provider updated",modelMapper.map(saveProvider,ProviderResponseDto.class));
        }
        return ResponseBuilder.getFailureResponse(HttpStatus.NOT_FOUND,"Providers not found Id: "+providerId);
    }


    @Override
    public Response deleteProvider(Long providerId) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Response getProviderInvoices(Long providerId) {
        var optionalProvider = providerRepository.findByIdAndIsActiveTrue(providerId);
//        var list = providerRepository.findByIdAndIsActiveTrue(providerId).get().getInvoiceList();
        if (optionalProvider.isPresent()){
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK,
                    "Provider Found ",optionalProvider.get().getInvoiceList());
        }
         return ResponseBuilder.getFailureResponse(HttpStatus.NOT_FOUND,"Provider Not found Id: "+providerId);

    }

    @Override
    public Response updateProviderInvoice(Long providerId, ParchedInvoice invoice) {

        return null;
    }
}

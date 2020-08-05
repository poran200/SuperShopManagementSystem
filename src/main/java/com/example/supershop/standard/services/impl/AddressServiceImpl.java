package com.example.supershop.standard.services.impl;

import com.example.supershop.dto.AddressDto;
import com.example.supershop.dto.respose.Response;
import com.example.supershop.exception.EntityNotFoundException;
import com.example.supershop.model.Address;
import com.example.supershop.repository.AddressRepository;
import com.example.supershop.standard.services.AddressService;
import com.example.supershop.util.ResponseBuilder;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service("AddressService")
public class  AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;
    public AddressServiceImpl(AddressRepository addressRepository, ModelMapper modelMapper) {
        this.addressRepository = addressRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Response save(AddressDto addressDto) {

        Address address = modelMapper.map(addressDto,Address.class);
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        Address saveAddress = addressRepository.save(address);
        if (isSave(saveAddress)){
            return ResponseBuilder.getSuccessResponse(HttpStatus.CREATED,"Address created",
                    modelMapper.map(address,AddressDto.class));
        }
        return ResponseBuilder.getFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR,"Internal server error");
    }

    @Override
    public Response update(Long Id, AddressDto addressDto) {
        return null;
    }

    @Override
    public Response delete(Long id) {
        Optional<Address> optionalAddress = getAddress(id);
        if (optionalAddress.isPresent()){
             optionalAddress.get().setIsActive(false);
            Address address = addressRepository.save(optionalAddress.get());
            if (isSave(address)){
                return ResponseBuilder.getSuccessResponse(HttpStatus.OK,"Address deleted successfully Id: "+id,null);
            }else {
                return ResponseBuilder.getFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR,"Internal server error");
            }
        }
        return ResponseBuilder.getFailureResponse(HttpStatus.NOT_FOUND,"Address not found "+ id);
    }

    private boolean isSave(Address saveAddress) {
        return saveAddress != null;
    }

    private Optional<Address> getAddress(Long id) {
        return addressRepository.findByIdAndIsActiveTrue(id);
    }

    @Override
    public Response getById(Long id) {
        Optional<Address> addressOptional = Optional.ofNullable(addressRepository.findByIdAndIsActiveTrue(id).orElseThrow(EntityNotFoundException::new));
        if (addressOptional.isPresent()){
//            AddressDto addressDto = new AddressDto(address.get().getId(),address.get().getCity(),address.get().getDetails());
            Address address = addressOptional.get();
            AddressDto addressDto = modelMapper.map(address,AddressDto.class);
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK,"address found",addressDto);
        }
        return ResponseBuilder.getFailureResponse(HttpStatus.NOT_FOUND,"address not found "+ id);
    }

    @Override
    public Response getAll() {
        List<Address> addressList = addressRepository.findAll();
        return ResponseBuilder.getSuccessResponseList(HttpStatus.OK,"All Address",
                Collections.singletonList(addressList),addressList.size());
    }

    @Override
    public Response getAll(Pageable pageable) {
        Page<Address> addressPage = addressRepository.findAll(pageable);
        return ResponseBuilder.getSuccessResponsePage(HttpStatus.OK,"page of address",addressPage);
    }

    @Override
    public Response getAllByAddressIsActiveTrue(Pageable pageable) {
        return ResponseBuilder.getSuccessResponsePage(HttpStatus.OK,
                "Page of address",
                addressRepository.findAllByIsActiveTrue(pageable));
    }
}

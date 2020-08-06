package com.example.supershop.standard.services;

import com.example.supershop.dto.AddressDto;
import com.example.supershop.dto.respose.Response;
import org.springframework.data.domain.Pageable;


public interface AddressService {
    Response save(AddressDto  addressDto);
    Response update(Long Id, AddressDto  addressDto);
    Response  delete(Long Id);
    Response  getById(Long Id);
    Response getAll();
    Response getAll(Pageable pageable);
    Response getAllByAddressIsActiveTrue(Pageable pageable);
}

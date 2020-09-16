package com.example.supershop.controller;

import com.example.supershop.anotation.DataValidation;
import com.example.supershop.anotation.PageAbleData;
import com.example.supershop.dto.AddressDto;
import com.example.supershop.dto.respose.Response;
import com.example.supershop.standard.services.AddressService;
import com.example.supershop.util.UrlConstrains;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(value = UrlConstrains.AddressManagement.ROOT)
public class AddressController {
    private final AddressService addressService;
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }
    @PostMapping(UrlConstrains.AddressManagement.CREATE)
    @DataValidation
    public Response create(@Valid @RequestBody AddressDto addressDto, BindingResult bindingResult){
        return addressService.save(addressDto);
    }
    @GetMapping(UrlConstrains.AddressManagement.FIND_BY_ID)
    public ResponseEntity<Response> getById(@PathVariable Long id){

        Response response = addressService.getById(id);
        if (response.getErrors()!=null){
           ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

    }
    @GetMapping(UrlConstrains.AddressManagement.ALL)
    public ResponseEntity<Response> geAll(){
        return ResponseEntity.ok(addressService.getAll());
    }

    @GetMapping(UrlConstrains.AddressManagement.PAGE)
    @PageAbleData
    public ResponseEntity<Response> geAllByPage(Pageable pageable){
        return ResponseEntity.ok(addressService.getAllByAddressIsActiveTrue(pageable));
    }
    @DeleteMapping(UrlConstrains.AddressManagement.DELETE)
    public  ResponseEntity<Response> deletedAddress(@PathVariable long id){
        Response response = addressService.delete(id);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }
}

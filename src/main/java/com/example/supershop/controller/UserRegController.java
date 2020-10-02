package com.example.supershop.controller;

import com.example.supershop.anotation.APiController;
import com.example.supershop.anotation.DataValidation;
import com.example.supershop.dto.request.UserDto;
import com.example.supershop.standard.services.UserService;
import com.example.supershop.util.UrlConstrains;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@APiController
@RequestMapping(UrlConstrains.UserReg.REGISTRATION)
public class UserRegController {
    private final UserService userService;

    public UserRegController(UserService userService) {
        this.userService = userService;
    }

    @DataValidation
    @PostMapping
    public ResponseEntity<Object> registration(@Valid @RequestBody UserDto dto, BindingResult result) {
        var response = userService.
                create(dto);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }
}

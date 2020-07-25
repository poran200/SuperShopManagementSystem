package com.example.supershop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping(value = "/")
public class HelloController {
    @GetMapping
    public RedirectView getSwaggerPage(){
        return  new RedirectView("swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/");
    }
 }

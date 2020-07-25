package com.example.supershop.config;

import com.github.javafaker.Faker;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class BeanConfiguration {

    @Bean
   public ModelMapper getModelMapper(){
       return new ModelMapper();
   }
   @Bean
   public Faker  getFaker(){
        return new Faker(new Locale("en-US"));
   }
}

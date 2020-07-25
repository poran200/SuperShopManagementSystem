package com.example.supershop.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException  {

   private   Object object;
    public EntityNotFoundException(String message) {
        super(message);
    }

    public <T>EntityNotFoundException( T o) {
         this.object = o;
    }

    public EntityNotFoundException() {

    }
}

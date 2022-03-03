package com.challenge.vendingmachine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ProductNotExistException extends RuntimeException{

    public ProductNotExistException() {
        super();
    }

    public ProductNotExistException(String message) {
        super(message);
    }
}

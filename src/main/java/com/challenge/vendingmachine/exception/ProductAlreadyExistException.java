package com.challenge.vendingmachine.exception;

public class ProductAlreadyExistException extends RuntimeException{

    public ProductAlreadyExistException() {
        super();
    }

    public ProductAlreadyExistException(String message) {
        super(message);
    }
}

package com.challenge.vendingmachine.exception;


public class NotEnoughQuantityException extends RuntimeException{

    public NotEnoughQuantityException() {
        super();
    }

    public NotEnoughQuantityException(String message) {
        super(message);
    }
}

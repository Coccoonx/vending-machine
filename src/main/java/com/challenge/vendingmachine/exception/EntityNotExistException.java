package com.challenge.vendingmachine.exception;


public class EntityNotExistException extends RuntimeException{

    public EntityNotExistException() {
        super();
    }

    public EntityNotExistException(String message) {
        super(message);
    }
}

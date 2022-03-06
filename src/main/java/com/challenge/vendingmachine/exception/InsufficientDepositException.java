package com.challenge.vendingmachine.exception;


public class InsufficientDepositException extends RuntimeException{

    public InsufficientDepositException() {
        super();
    }

    public InsufficientDepositException(String message) {
        super(message);
    }
}

package com.challenge.vendingmachine.exception;


public class UserSessionExistException extends RuntimeException{

    public UserSessionExistException() {
        super();
    }

    public UserSessionExistException(String message) {
        super(message);
    }
}

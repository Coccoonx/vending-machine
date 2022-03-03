package com.challenge.vendingmachine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "You must give a valid id")
public class ProductNotExistException extends RuntimeException{
}

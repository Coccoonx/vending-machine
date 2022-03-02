package com.challenge.vendingmachine.validator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FiveMultipleValidator implements ConstraintValidator<FiveMultiple, Double> {

    @Override
    public boolean isValid(Double aDouble, ConstraintValidatorContext constraintValidatorContext) {
        return aDouble != null && aDouble % 5 == 0;
    }
}

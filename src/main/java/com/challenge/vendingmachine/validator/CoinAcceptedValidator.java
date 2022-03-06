package com.challenge.vendingmachine.validator;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.*;

@Component
public class CoinAcceptedValidator implements ConstraintValidator<CoinAccepted, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        Integer[] accepted = new Integer[]{5, 10, 20, 50, 100};
        Collection<Integer> validCoins = new ArrayList<>(Arrays.asList(accepted));
        return value != null && validCoins.contains(value);
    }
}

package com.challenge.vendingmachine.validator;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.*;

import static com.challenge.vendingmachine.utils.VMConstants.ACCEPTED_COINS;

@Component
public class CoinAcceptedValidator implements ConstraintValidator<CoinAccepted, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        Collection<Integer> validCoins = new ArrayList<>(Arrays.asList(ACCEPTED_COINS));
        return value != null && validCoins.contains(value);
    }
}

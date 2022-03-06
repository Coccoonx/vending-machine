package com.challenge.vendingmachine.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CoinAcceptedValidator.class)
public @interface CoinAccepted {
    //error message
     String message() default "Invalid value: should be 5, 10, 20, 50 and 100 cent";
    //represents group of constraints
     Class<?>[] groups() default {};
    //represents additional information about annotation
     Class<? extends Payload>[] payload() default {};
}

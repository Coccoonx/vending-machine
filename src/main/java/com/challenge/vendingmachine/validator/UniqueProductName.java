package com.challenge.vendingmachine.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueProductNameValidator.class)
public @interface UniqueProductName {
    //error message
     String message() default "Invalid name: should be unique";
    //represents group of constraints
     Class<?>[] groups() default {};
    //represents additional information about annotation
     Class<? extends Payload>[] payload() default {};
}

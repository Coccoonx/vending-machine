package com.challenge.vendingmachine.validator;

import com.challenge.vendingmachine.repository.ProductRepository;
import com.challenge.vendingmachine.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UniqueProductNameValidator implements ConstraintValidator<UniqueProductName, String> {

    @Autowired
    private ProductService productService;

//    public UniqueProductNameValidator(ProductRepository productRepository) {
//        this.productRepository = productRepository;
//    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s != null && productService.findByProductName(s) != null ;
    }
}

package com.challenge.vendingmachine.repository;

import com.challenge.vendingmachine.model.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@RunWith(SpringRunner.class)

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;


    @Test(expected = ConstraintViolationException.class)
    public void avoidNonMultipleOfFive(){
        Product p = new Product();
        p.setCost(12);
        productRepository.save(p);
    }

    @Test
    public void saveMultipleOfFiveCost(){
        Product p = new Product();
        p.setCost(15);
        Product saved = productRepository.save(p);

        assertEquals( p.getCost(), saved.getCost(), 0);
    }

    @Test
    public void findByProductName(){
        Product p = new Product();
        p.setProductName("Orange");
        productRepository.save(p);

        Product product = productRepository.findByProductName("Orange");

        assertNotNull( product);
    }
}

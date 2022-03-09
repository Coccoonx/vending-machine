package com.challenge.vendingmachine.repository;

import com.challenge.vendingmachine.model.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@RunWith(SpringRunner.class)

@DataJpaTest
@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;


    @Test
    public void saveProduct(){
        Product p = new Product();
        p.setProductName("Orange");
        productRepository.save(p);

        assertEquals(1, productRepository.findAll().size());
        assertEquals("Orange", productRepository.findAll().get(0).getProductName());
    }

    @Test
    public void findAll(){
        Product p = new Product();
        p.setProductName("Orange");
        productRepository.save(p);

        p = new Product();
        p.setProductName("Apple");
        productRepository.save(p);

        assertEquals(2, productRepository.findAll().size());
    }

    @Test
    public void findByProductName(){
        Product p = new Product();
        p.setProductName("Orange");
        productRepository.save(p);
        Product product = productRepository.findByProductNameIgnoreCase("Orange");

        assertNotNull(product);
        assertEquals("Orange", product.getProductName());
    }

    @Test(expected = ConstraintViolationException.class)
    public void avoidNonMultipleOfFive(){
        Product p = new Product();
        p.setCost(12);
        productRepository.save(p);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void avoidNonMultipleDuplicateProductName(){
        Product p = new Product();
        p.setCost(15);
        p.setProductName("Orange");
        productRepository.save(p);

        p = new Product();
        p.setCost(10);
        p.setProductName("Orange");
        productRepository.save(p);

    }

    @Test
    public void saveMultipleOfFiveCost(){
        Product p = new Product();
        p.setProductName("Apple");
        p.setCost(15);
        Product saved = productRepository.save(p);

        assertEquals( p.getCost(), saved.getCost(), 0);
    }


}

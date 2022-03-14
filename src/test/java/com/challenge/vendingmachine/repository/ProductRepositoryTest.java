package com.challenge.vendingmachine.repository;

import com.challenge.vendingmachine.model.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;


    @Test
    public void saveProduct_Success(){
        Product product = new Product();
        product.setProductName("Orange");
        productRepository.save(product);

        assertEquals("Orange", productRepository.findAll().get(0).getProductName());
    }

    @Test
    public void saveProductDuplicatedName_Failed(){
        Product product1 = new Product();
        product1.setProductName("Orange");
        productRepository.save(product1);

        Product product2 = new Product();
        product2.setProductName("Orange");

        RuntimeException ex = assertThrows(RuntimeException.class, () -> productRepository.save(product2));

        assertInstanceOf(DataIntegrityViolationException.class, ex);
    }

    @Test
    public void findAll(){
        Product product1 = new Product();
        product1.setProductName("Orange");
        productRepository.save(product1);

        Product product2 = new Product();
        product2.setProductName("Apple");
        productRepository.save(product2);

        assertEquals(2, productRepository.findAll().size());
    }

    @Test
    public void findByProductName(){
        Product product = new Product();
        product.setProductName("Orange");
        productRepository.save(product);
        Product productSaved = productRepository.findByProductNameIgnoreCase("Orange");

        assertEquals("Orange", productSaved.getProductName());
    }

    @Test(expected = ConstraintViolationException.class)
    public void avoidNonMultipleOfFive_Success(){
        Product product = new Product();
        product.setCost(12);
        productRepository.save(product);
    }

    @Test
    public void saveMultipleOfFiveCost_Success(){
        Product p = new Product();
        p.setProductName("Apple");
        p.setCost(15);
        Product saved = productRepository.save(p);

        assertEquals( p.getCost(), saved.getCost(), 0);
    }


}

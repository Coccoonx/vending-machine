package com.challenge.vendingmachine.service;

import com.challenge.vendingmachine.exception.EntityNotExistException;
import com.challenge.vendingmachine.model.Product;
import com.challenge.vendingmachine.repository.ProductRepository;
import com.challenge.vendingmachine.service.impl.ProductServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productServiceImpl;


    Product one, two, three;
    List<Product> list;

    @Before
    public void init() {
        one = new Product();
        one.setId(1L);
        one.setProductName("Orange");
        one.setCost(15);

        two = new Product();
        two.setId(2L);
        two.setProductName("Apple");
        two.setCost(20);

        three = new Product();
        three.setId(3L);
        three.setProductName("Pineapple");
        three.setCost(35);

        list= new ArrayList<>();
        list.add(one);
        list.add(two);
        list.add(three);
    }

    @Test
    public void createProduct_Success() {

        when(productRepository.findByProductNameIgnoreCase(one.getProductName())).thenReturn(null);
        when(productRepository.save(one)).thenReturn(one);

        Product created = productServiceImpl.create(one);

        verify(productRepository, times(1)).findByProductNameIgnoreCase("Orange");
        verify(productRepository, times(1)).save(created);

        assertNotNull(created);
        assertEquals("Orange", created.getProductName());
    }

    @Test
    public void createProduct_Failed_With_Exception() {

        when(productRepository.findByProductNameIgnoreCase(one.getProductName())).thenReturn(one);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> productServiceImpl.create(one));

        assertEquals("Product name " + one.getProductName() + " already exist", ex.getMessage());

    }

    @Test
    public void updateProduct_Failed_With_Exception() {

        when(productRepository.findById(one.getId())).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> productServiceImpl.update(one.getId(), one));

        assertInstanceOf(EntityNotExistException.class, ex);
        assertEquals("Invalid product id : " + one.getId(), ex.getMessage());
    }

    @Test
    public void updateProduct_Success() {

        when(productRepository.findById(one.getId())).thenReturn(Optional.of(one));
        when(productRepository.save(one)).thenReturn(one);

        Product update = productServiceImpl.update(one.getId(), one);

        verify(productRepository, times(1)).save(update);

        assertNotNull(update);
        assertEquals("Orange", update.getProductName());
    }


    @Test
    public void deleteProduct_Failed_With_Exception() {

        when(productRepository.findById(one.getId())).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> productServiceImpl.delete(one.getId()));

        assertInstanceOf(EntityNotExistException.class, ex);
        assertEquals("Invalid product id: " + one.getId(), ex.getMessage());
    }


    @Test
    public void deleteProduct_Success() {

        when(productRepository.findById(one.getId())).thenReturn(Optional.of(one));

        productServiceImpl.delete(one.getId());

        verify(productRepository, times(1)).delete(one);

    }

    @Test
    public void findProductByName_Success() {

        when(productRepository.findByProductNameIgnoreCase(one.getProductName())).thenReturn(one);

        Product p = productServiceImpl.findByProductName(one.getProductName());

        verify(productRepository, times(1)).findByProductNameIgnoreCase(one.getProductName());

        assertEquals("Orange", p.getProductName());

    }

    @Test
    public void findProductById_Success() {

        when(productRepository.findById(one.getId())).thenReturn(Optional.of(one));

        Product p = productServiceImpl.findById(one.getId());

        verify(productRepository, times(2)).findById(one.getId());

        assertEquals("Orange", p.getProductName());

    }

    @Test
    public void findProductByIdNotFound_Success() {

        when(productRepository.findById(one.getId())).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> productServiceImpl.findById(one.getId()));
        verify(productRepository, times(1)).findById(one.getId());

        assertInstanceOf(EntityNotExistException.class, ex);
        assertEquals("Invalid product id: " + one.getId(), ex.getMessage());

    }

    @Test
    public void findProductAlll_Success() {

        when(productRepository.findAll()).thenReturn(list);

        List<Product> productList =  productServiceImpl.findAll();

        verify(productRepository, times(1)).findAll();

        assertNotNull(productList);
        assertEquals(3, productList.size());

    }

}

package com.challenge.vendingmachine.controller;

import com.challenge.vendingmachine.model.Product;
import com.challenge.vendingmachine.service.ProductService;
import com.challenge.vendingmachine.service.mapper.ProductMapper;
import com.challenge.vendingmachine.service.mapper.ProductMapperImpl;
import com.challenge.vendingmachine.service.mapper.UserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
@ContextConfiguration(classes = {
        ProductMapperImpl.class,
        UserMapper.class,
})
public class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    ProductService productService;

    ArrayList<Product> products;

    @Before
    public void init() {
        products = new ArrayList<>();
        Product one = new Product();
        one.setId(1L);
        one.setProductName("Banana");
        one.setCost(9);
        one.setAmountAvailable(25);
        products.add(one);

        Product two = new Product();
        two.setId(2L);
        two.setProductName("Orange");
        two.setCost(10);
        two.setAmountAvailable(25);
        products.add(two);

        Product three = new Product();
        three.setId(3L);
        three.setProductName("Pineapple");
        three.setCost(11);
        three.setAmountAvailable(25);
        products.add(three);
    }

    @Test
    public void testGetAll_Success() throws Exception {
        Mockito.when(productService.findAll()).thenReturn(products);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].cost", equalTo(11.0)));
    }


    @Test
    public void testCreate_Success() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setProductName("Banana");
        product.setCost(20);
        product.setAmountAvailable(25);
        Mockito.when(productService.create(product)).thenReturn(product);


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/products")
                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(product));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$", nullValue()));
//                .andExpect(jsonPath("$.cost", equalTo(20.0)));
    }

    @Test
    public void testCreate_Fail() throws Exception {
        Product one = new Product();
        one.setId(1L);
        one.setProductName("Banana");
        one.setCost(18);
        one.setAmountAvailable(25);
        Mockito.when(productService.create(one)).thenReturn(one);


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(one));

        mockMvc.perform(mockRequest)
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.cost", equalTo("Invalid cost: should be multiple of 5")));
    }


}
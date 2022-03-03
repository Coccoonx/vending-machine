package com.challenge.vendingmachine.service;

import com.challenge.vendingmachine.model.Product;


public interface ProductService extends ICrudDefServiceInterface<Product, Long>{

   Product findByProductName(String productName);
}

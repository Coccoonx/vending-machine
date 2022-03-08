package com.challenge.vendingmachine.service;

import com.challenge.vendingmachine.model.Product;


public interface ProductService extends CrudDefService<Product, Long> {

   Product findByProductName(String productName);
}

package com.challenge.vendingmachine.service;

import com.challenge.vendingmachine.model.Product;
import com.challenge.vendingmachine.model.Purchase;
import com.challenge.vendingmachine.model.User;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;


public interface PurchaseService extends CrudDefService<Purchase, Long> {

   List<Purchase> findByBuyer(@NotNull User buyer);

   List<Purchase> findByProduct(@NotNull Product product);

   List<Purchase> findByDate(@NotNull Date date);
}

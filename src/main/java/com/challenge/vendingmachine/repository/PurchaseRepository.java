package com.challenge.vendingmachine.repository;

import com.challenge.vendingmachine.model.Product;
import com.challenge.vendingmachine.model.Purchase;
import com.challenge.vendingmachine.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {


    List<Purchase> findByBuyer(User buyer);

    List<Purchase> findByProduct(User buyer);

    List<Purchase> findByDate(Date date);



}

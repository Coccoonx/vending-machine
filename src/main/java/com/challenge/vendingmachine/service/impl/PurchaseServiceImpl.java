package com.challenge.vendingmachine.service.impl;

import com.challenge.vendingmachine.exception.EntityNotExistException;
import com.challenge.vendingmachine.model.Product;
import com.challenge.vendingmachine.model.Purchase;
import com.challenge.vendingmachine.model.User;
import com.challenge.vendingmachine.repository.PurchaseRepository;
import com.challenge.vendingmachine.service.PurchaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);


    @Autowired
    private PurchaseRepository purchaseRepository;

    @Override
    public Purchase create(Purchase purchase) {
        if (purchase.getId() != null) {
            throw new IllegalArgumentException("Cannot create existing purchase");
        }
        LOGGER.info("Creating purchase {}", purchase);
        return purchaseRepository.save(purchase);
    }

    @Override
    public Purchase update(Long id, Purchase purchase) {
        if (id == null || !purchaseRepository.findById(id).isPresent())
            throw new EntityNotExistException("Invalid purchase id : " + id);
        LOGGER.info("Updating purchase {}", purchase);
        return purchaseRepository.save(purchase);
    }

    @Override
    public void delete(Long id) {
        Purchase p = findById(id);
        if (p != null) {
            LOGGER.info("Deleting product with id {}", id);
            purchaseRepository.delete(p);
        } else
            throw new EntityNotExistException("Invalid purchase id: " + id);
    }

    @Override
    public List<Purchase> findAll() {
        LOGGER.info("find All Purchase");
        return purchaseRepository.findAll();
    }

    @Override
    public Page<Purchase> findAllPaged(Pageable pageable) {
        LOGGER.info("find All Purchase paged");
        return purchaseRepository.findAll(pageable);
    }

    @Override
    public Purchase findById(Long id) {
        LOGGER.info("find product by id {}", id);
        if (purchaseRepository.findById(id).isPresent())
            return purchaseRepository.findById(id).get();
        else
            throw new EntityNotExistException("Invalid purchase id: " + id);
    }

    @Override
    public List<Purchase> findByBuyer(User buyer) {
        LOGGER.info("find All by buyer "+ buyer.getUsername());
        return purchaseRepository.findByBuyer(buyer);
    }

    @Override
    public List<Purchase> findByProduct(Product product) {
        LOGGER.info("find All by product "+ product.getProductName());

        return purchaseRepository.findByProduct(product);
    }

    @Override
    public List<Purchase> findByDate(Date date) {
        LOGGER.info("find All by date "+ date);
        return purchaseRepository.findByDate(date);
    }
}

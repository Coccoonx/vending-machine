package com.challenge.vendingmachine.service.impl;

import com.challenge.vendingmachine.exception.InsufficientDepositException;
import com.challenge.vendingmachine.model.Product;
import com.challenge.vendingmachine.model.Purchase;
import com.challenge.vendingmachine.model.User;
import com.challenge.vendingmachine.repository.PurchaseRepository;
import com.challenge.vendingmachine.repository.UserRepository;
import com.challenge.vendingmachine.service.BuyerService;
import com.challenge.vendingmachine.service.dto.BuyResponse;
import com.challenge.vendingmachine.service.dto.Coin;
import com.challenge.vendingmachine.service.mapper.ProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.challenge.vendingmachine.utils.VMConstants.ACCEPTED_COINS;

@Service
public class BuyerServiceImpl implements BuyerService {

    private static final Logger log = LoggerFactory.getLogger(BuyerServiceImpl.class);


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public User deposit(User user, Coin coin) {

        log.info("Deposit of {} for {}", coin.getValue(), user.getUsername());
        long sum = user.getDeposit() + coin.getValue();
        user.setDeposit(sum);
        return userRepository.save(user);
    }


    @Override
    public BuyResponse buyProduct(User user, Product product, int quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException("Invalid quantity");
        }

        //check if deposit allows this purchase
        if (user.getDeposit() < quantity * product.getCost()) {
            double balance = (quantity * product.getCost()) - user.getDeposit();
            throw new InsufficientDepositException("Insufficient Deposit: need a deposit of " + balance);
        }

        Purchase purchase = new Purchase();
        purchase.setDate(new Date());
        purchase.setBuyer(user);
        purchase.setProduct(product);
        purchase.setQuantity(quantity);

        purchaseRepository.save(purchase);

        long previousDeposit = user.getDeposit();
        long totalSpent = quantity * product.getCost();
        long balance = previousDeposit - totalSpent;

        user.setDeposit(balance);
        userRepository.save(user);

        BuyResponse data = new BuyResponse();
        data.setProduct(productMapper.toDTO(product));
        data.setTotalSpent(totalSpent);
        if (balance > 0)
            data.setChanges(computeChanges(balance));

        return data;
    }

    @Override
    public User reset(User user) {
        log.info("Reset deposit of {}", user.getUsername());
        user.setDeposit(0);
        return userRepository.save(user);
    }

    private List<Integer> computeChanges(long balance) {
        long remaining = balance;

        List<Integer> changes = new ArrayList<>();
        for (int i = (ACCEPTED_COINS.length - 1); i >= 0; i--) {
            long occurrence = (remaining % ACCEPTED_COINS[i]);
            if (occurrence > 0) {
                remaining = remaining - occurrence * ACCEPTED_COINS[i];
                while (occurrence > 0) {
                    changes.add(ACCEPTED_COINS[i]);
                    occurrence--;
                }
            }
        }
        return changes;
    }
}

package com.challenge.vendingmachine.service.impl;

import com.challenge.vendingmachine.model.User;
import com.challenge.vendingmachine.repository.UserRepository;
import com.challenge.vendingmachine.service.BuyerService;
import com.challenge.vendingmachine.service.dto.Coin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuyerServiceImpl implements BuyerService {

    private static final Logger log = LoggerFactory.getLogger(BuyerServiceImpl.class);


    @Autowired
    private UserRepository userRepository;

    @Override
    public User deposit(User user, Coin coin) {

        log.info("Deposit of {} for {}", coin.getValue(), user.getUsername());
        double sum = user.getDeposit() + coin.getValue();
        user.setDeposit(sum);
        return userRepository.save(user);
    }

    @Override
    public User reset(User user) {
        log.info("Reset deposit of {}", user.getUsername());
        user.setDeposit(0);
        return userRepository.save(user);
    }
}

package com.challenge.vendingmachine.service;

import com.challenge.vendingmachine.model.User;
import com.challenge.vendingmachine.service.dto.Coin;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface BuyerService {
    User deposit(@NotNull User user, @Valid Coin coin);

    User reset(@NotNull User user);
}

package com.challenge.vendingmachine.service;

import com.challenge.vendingmachine.model.Product;
import com.challenge.vendingmachine.model.User;
import com.challenge.vendingmachine.service.dto.BuyResponse;
import com.challenge.vendingmachine.service.dto.Coin;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface BuyerService {
    User deposit(@NotNull User user, @Valid Coin coin);

    BuyResponse buyProduct(@NotNull User user, @NotNull Product product, int quantity);

    User reset(@NotNull User user);
}

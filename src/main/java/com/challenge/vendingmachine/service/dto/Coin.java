package com.challenge.vendingmachine.service.dto;

import com.challenge.vendingmachine.validator.CoinAccepted;

public class Coin {
    @CoinAccepted
    private Integer value;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Coin{" +
                "value=" + value +
                '}';
    }
}

package com.challenge.vendingmachine.utils;

import java.util.ArrayList;
import java.util.List;

import static com.challenge.vendingmachine.utils.VMConstants.ACCEPTED_COINS;

public class BuyUtils {

    public static List<Integer> computeChanges(long balance) {
        long remaining = balance;

        List<Integer> changes = new ArrayList<>();
        for (int i = (ACCEPTED_COINS.length - 1); i >= 0; i--) {
            if (remaining >= ACCEPTED_COINS[i]) {
                long occurrence = (remaining / ACCEPTED_COINS[i]);
                if (occurrence > 0) {
                    remaining = remaining - occurrence * ACCEPTED_COINS[i];
                    while (occurrence > 0) {
                        changes.add(ACCEPTED_COINS[i]);
                        occurrence--;
                    }
                }
            }
        }
        return changes;
    }
}

package com.challenge.vendingmachine.service;

import com.challenge.vendingmachine.utils.BuyUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
public class BuyServiceTest {



    @Test
    public void computeChangeTest(){
        long balance = 195;

        List<Integer> changes = BuyUtils.computeChanges(balance);

        assertNotNull(changes);
        assertEquals(5, changes.size());
    }
}

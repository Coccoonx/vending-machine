package com.challenge.vendingmachine.service.dto;

import com.challenge.vendingmachine.model.Product;

import java.util.ArrayList;
import java.util.List;

public class BuyResponse {
    private double totalSpent;

    private ProductDTO product;

    private List<Integer> changes = new ArrayList<>();

    public double getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(double totalSpent) {
        this.totalSpent = totalSpent;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public List<Integer> getChanges() {
        return changes;
    }

    public void setChanges(List<Integer> changes) {
        this.changes = changes;
    }
}

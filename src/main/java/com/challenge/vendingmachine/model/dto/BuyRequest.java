package com.challenge.vendingmachine.model.dto;

import javax.validation.constraints.NotNull;

public class BuyRequest {

    private int quantity;

    @NotNull
    private Long productId;

    public BuyRequest() {
    }

    public BuyRequest(Long productId, int quantity) {
        this.quantity = quantity;
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}

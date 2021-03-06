package com.challenge.vendingmachine.model.dto;

import com.challenge.vendingmachine.model.Product;
import com.challenge.vendingmachine.validator.FiveMultiple;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class ProductDTO implements Serializable {

    private Long id;

    private double amountAvailable;

    @FiveMultiple
    private long cost;

    @NotEmpty(message = "Name may not be empty")
    @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
    private String productName;

    private Long sellerId;

    private UserDTO seller;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmountAvailable() {
        return amountAvailable;
    }

    public void setAmountAvailable(double amountAvailable) {
        this.amountAvailable = amountAvailable;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public UserDTO getSeller() {
        return seller;
    }

    public void setSeller(UserDTO seller) {
        this.seller = seller;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
                ", amountAvailable=" + amountAvailable +
                ", cost=" + cost +
                ", productName='" + productName + '\'' +
                ", seller=" + seller +
                '}';
    }
}

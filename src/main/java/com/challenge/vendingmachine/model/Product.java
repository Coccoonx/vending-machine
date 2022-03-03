package com.challenge.vendingmachine.model;

import com.challenge.vendingmachine.validator.FiveMultiple;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    private double amountAvailable;

    @FiveMultiple
    private double cost;

    @Column(unique = true)
    @NotEmpty(message = "Name may not empty")
    @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
    private String productName;

    private Long sellerId;

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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
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

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", amountAvailable=" + amountAvailable +
                ", cost=" + cost +
                ", productName='" + productName + '\'' +
                ", sellerId=" + sellerId +
                '}';
    }
}

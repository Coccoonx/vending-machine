package com.challenge.vendingmachine.model.dto;

import java.io.Serializable;
import java.util.Date;

public class PurchaseDTO implements Serializable {

    private Long id;

    private Date date;

    private int quantity;

    private long spent;

    private ProductDTO product;

    private UserDTO buyer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getSpent() {
        return spent;
    }

    public void setSpent(long spent) {
        this.spent = spent;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public UserDTO getBuyer() {
        return buyer;
    }

    public void setBuyer(UserDTO buyer) {
        this.buyer = buyer;
    }
}

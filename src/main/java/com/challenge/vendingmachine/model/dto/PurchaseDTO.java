package com.challenge.vendingmachine.model.dto;

import java.io.Serializable;
import java.util.Date;

public class PurchaseDTO implements Serializable {

    private Long id;

    private Date date;

    private int quantity;

    private ProductDTO productDto;

    private UserDTO buyerDto;

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

    public ProductDTO getProductDto() {
        return productDto;
    }

    public void setProductDto(ProductDTO productDto) {
        this.productDto = productDto;
    }

    public UserDTO getBuyerDto() {
        return buyerDto;
    }

    public void setBuyerDto(UserDTO buyerDto) {
        this.buyerDto = buyerDto;
    }
}

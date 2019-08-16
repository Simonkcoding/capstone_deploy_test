package com.company.SimonKwokU1Capstone.model;

import java.math.BigDecimal;

public abstract class PurchaseItem {
    private BigDecimal price;
    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

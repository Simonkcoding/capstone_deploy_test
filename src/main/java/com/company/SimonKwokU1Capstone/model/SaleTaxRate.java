package com.company.SimonKwokU1Capstone.model;

import java.math.BigDecimal;
import java.util.Objects;

public class SaleTaxRate {


//    state char(2) not null,
//    rate decimal(3,2) not null

    private String state;
    private BigDecimal rate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaleTaxRate taxRate = (SaleTaxRate) o;
        return state.equals(taxRate.state) &&
                rate.equals(taxRate.rate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, rate);
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}

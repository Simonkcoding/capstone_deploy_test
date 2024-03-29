package com.company.SimonKwokU1Capstone.model;

import java.math.BigDecimal;
import java.util.Objects;

public class ProcessingFee {

//    product_type varchar(20) not null,
//    fee decimal (4,2)

    private String productType;
    private BigDecimal fee;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessingFee that = (ProcessingFee) o;
        return productType.equals(that.productType) &&
                fee.equals(that.fee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productType, fee);
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }
}

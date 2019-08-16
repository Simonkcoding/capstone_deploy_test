package com.company.SimonKwokU1Capstone.viewmodel;

import com.company.SimonKwokU1Capstone.model.PurchaseItem;


import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.Objects;

public class PurchaseItemViewModel {

    private int invoiceId;
    @NotEmpty(message = "Please supply a value for name")
    private String name;
    @NotEmpty(message = "Please supply a value for street")
    private String street;
    @NotEmpty(message = "Please supply a value for city")
    private String city;
    @NotEmpty(message = "Please supply a value for state")
    private String state;
    @NotEmpty(message = "Please supply a value for zipcode")
    private String zipCode;
    @NotEmpty(message = "Please supply a value for item type")
    private String itemType;
    @Digits(integer=10, fraction=0, message = "improper id format")
    @Min(value = 0, message = "id must be greater than 0")
    private int itemId;
    @NotNull
    @Min(value = 1, message = "quantity must be greater than 1")
    private int quantity;

    private PurchaseItem purchaseItems;

    public PurchaseItem getPurchaseItems() {
        return purchaseItems;
    }

    public void setPurchaseItems(PurchaseItem purchaseItems) {
        this.purchaseItems = purchaseItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseItemViewModel that = (PurchaseItemViewModel) o;
        return invoiceId == that.invoiceId &&
                itemId == that.itemId &&
                quantity == that.quantity &&
                name.equals(that.name) &&
                street.equals(that.street) &&
                city.equals(that.city) &&
                state.equals(that.state) &&
                zipCode.equals(that.zipCode) &&
                itemType.equals(that.itemType) &&
                purchaseItems.equals(that.purchaseItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceId, name, street, city, state, zipCode, itemType, itemId, quantity, purchaseItems);
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

//    public BigDecimal getUnitPrice() {
//        return unitPrice;
//    }
//
//    public void setUnitPrice(BigDecimal unitPrice) {
//        this.unitPrice = unitPrice;
//    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

//    public BigDecimal getSubtotal() {
//        return subtotal;
//    }
//
//    public void setSubtotal(BigDecimal subtotal) {
//        this.subtotal = subtotal;
//    }
//
//    public BigDecimal getTax() {
//        return tax;
//    }
//
//    public void setTax(BigDecimal tax) {
//        this.tax = tax;
//    }
//
//    public BigDecimal getProcessFee() {
//        return processFee;
//    }
//
//    public void setProcessFee(BigDecimal processFee) {
//        this.processFee = processFee;
//    }
//
//    public BigDecimal getTotal() {
//        return total;
//    }
//
//    public void setTotal(BigDecimal total) {
//        this.total = total;
//    }

}

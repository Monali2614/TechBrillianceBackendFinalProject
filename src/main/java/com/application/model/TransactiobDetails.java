package com.application.model;

import javax.persistence.Entity;


public class TransactiobDetails {

    private String orderId;
    private String currency;
    private Integer amount;
    private String KEY;

    public TransactiobDetails(String orderId, String currency, Integer amount ,String KEY) {
        this.orderId = orderId;
        this.currency = currency;
        this.amount = amount;
        this.KEY = KEY;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getKEY() {
        return KEY;
    }

    public void setKEY(String KEY) {
        this.KEY = KEY;
    }
}

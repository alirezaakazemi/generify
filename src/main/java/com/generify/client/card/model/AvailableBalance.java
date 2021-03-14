package com.generify.client.card.model;

public class AvailableBalance {
    private String currency;
    private long value;

    public AvailableBalance() {
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}

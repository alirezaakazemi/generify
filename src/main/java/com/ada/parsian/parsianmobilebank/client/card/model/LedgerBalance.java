package com.ada.parsian.parsianmobilebank.client.card.model;

public class LedgerBalance {
    private String currency;
    private long value;

    public LedgerBalance() {
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

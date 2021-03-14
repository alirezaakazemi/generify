package com.generify.client.card.model;

public class BankHotCardRequest implements IBankCardRequest {

    private String pan;
    private String reason;

    public BankHotCardRequest() {
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

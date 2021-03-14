package com.generify.model.card;

public class ClientHotCardRequest implements IClientCardRequest {
    private String pan;
    private String reason;

    public ClientHotCardRequest() {
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

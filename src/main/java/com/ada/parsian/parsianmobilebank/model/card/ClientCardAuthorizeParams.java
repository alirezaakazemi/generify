package com.ada.parsian.parsianmobilebank.model.card;

public class ClientCardAuthorizeParams {
    private String cvv2;
    private String expDate;
    private String pin;

    public ClientCardAuthorizeParams() {
    }

    public String getCvv2() {
        return cvv2;
    }

    public void setCvv2(String cvv2) {
        this.cvv2 = cvv2;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}

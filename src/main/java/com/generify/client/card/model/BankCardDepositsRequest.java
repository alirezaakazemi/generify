package com.generify.client.card.model;

public class BankCardDepositsRequest implements IBankCardRequest {
    private String cif;
    private String pan;

    public BankCardDepositsRequest() {
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

}

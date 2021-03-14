package com.generify.client.card.model;

public class BankCardOwnerRequest implements IBankCardRequest {
    private String destinationPan;
    private String pan;
    private CardAuthorizeParams cardAuthorizeParams;

    public BankCardOwnerRequest() {
    }

    public String getDestinationPan() {
        return destinationPan;
    }

    public void setDestinationPan(String destinationPan) {
        this.destinationPan = destinationPan;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public CardAuthorizeParams getCardAuthorizeParams() {
        return cardAuthorizeParams;
    }

    public void setCardAuthorizeParams(CardAuthorizeParams cardAuthorizeParams) {
        this.cardAuthorizeParams = cardAuthorizeParams;
    }
}

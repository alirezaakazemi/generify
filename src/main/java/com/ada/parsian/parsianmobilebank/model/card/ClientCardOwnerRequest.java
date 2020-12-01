package com.ada.parsian.parsianmobilebank.model.card;

public class ClientCardOwnerRequest implements IClientCardRequest {

    private String destinationPan;
    private String pan;
    private ClientCardAuthorizeParams cardAuthorizeParams;

    public ClientCardOwnerRequest() {
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

    @Override
    public ClientCardAuthorizeParams getCardAuthorizeParams() {
        return cardAuthorizeParams;
    }

    @Override
    public void setCardAuthorizeParams(ClientCardAuthorizeParams cardAuthorizeParams) {
        this.cardAuthorizeParams = cardAuthorizeParams;
    }
}

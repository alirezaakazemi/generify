package com.ada.parsian.parsianmobilebank.model.card;

public class ClientCardDepositsRequest implements IClientCardRequest {

    private String cif;
    private String pan;

    public ClientCardDepositsRequest() {
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

    @Override
    public ClientCardAuthorizeParams getCardAuthorizeParams() {
        return null;
    }

    @Override
    public void setCardAuthorizeParams(ClientCardAuthorizeParams cardAuthorizeParams) {

    }
}

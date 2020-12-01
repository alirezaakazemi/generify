package com.ada.parsian.parsianmobilebank.model.card;

public class ClientCardBalanceInquiryRequest implements IClientCardRequest {

    private String depositNumber;
    private String pan;
    private ClientCardAuthorizeParams cardAuthorizeParams;

    public ClientCardBalanceInquiryRequest() {
    }

    public String getDepositNumber() {
        return depositNumber;
    }

    public void setDepositNumber(String depositNumber) {
        this.depositNumber = depositNumber;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public ClientCardAuthorizeParams getCardAuthorizeParams() {
        return cardAuthorizeParams;
    }

    public void setCardAuthorizeParams(ClientCardAuthorizeParams cardAuthorizeParams) {
        this.cardAuthorizeParams = cardAuthorizeParams;
    }
}

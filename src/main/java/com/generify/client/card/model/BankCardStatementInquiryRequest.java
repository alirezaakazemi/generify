package com.generify.client.card.model;

public class BankCardStatementInquiryRequest implements IBankCardRequest {

    private String depositNumber;
    private String pan;
    private CardAuthorizeParams cardAuthorizeParams;

    public BankCardStatementInquiryRequest() {
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

    public CardAuthorizeParams getCardAuthorizeParams() {
        return cardAuthorizeParams;
    }

    public void setCardAuthorizeParams(CardAuthorizeParams cardAuthorizeParams) {
        this.cardAuthorizeParams = cardAuthorizeParams;
    }
}

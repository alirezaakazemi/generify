package com.ada.parsian.parsianmobilebank.client.card.model;

public class BankCardPayBillRequest implements IBankCardRequest {

    private String billId;
    private String payId;
    private CardAuthorizeParams cardAuthorizeParams;
    private String pan;

    public BankCardPayBillRequest() {
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public CardAuthorizeParams getCardAuthorizeParams() {
        return cardAuthorizeParams;
    }

    public void setCardAuthorizeParams(CardAuthorizeParams cardAuthorizeParams) {
        this.cardAuthorizeParams = cardAuthorizeParams;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }
}
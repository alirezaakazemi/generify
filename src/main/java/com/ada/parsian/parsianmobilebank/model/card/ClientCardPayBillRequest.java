package com.ada.parsian.parsianmobilebank.model.card;

public class ClientCardPayBillRequest implements IClientCardRequest {

    private String billId;
    private String payId;
    private ClientCardAuthorizeParams cardAuthorizeParams;
    private String pan;

    public ClientCardPayBillRequest() {
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

    public ClientCardAuthorizeParams getCardAuthorizeParams() {
        return cardAuthorizeParams;
    }

    public void setCardAuthorizeParams(ClientCardAuthorizeParams cardAuthorizeParams) {
        this.cardAuthorizeParams = cardAuthorizeParams;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }
}

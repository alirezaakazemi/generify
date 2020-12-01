package com.ada.parsian.parsianmobilebank.model.card;

public class ClientCardChargeRequest implements IClientCardRequest {

    private String productId;
    private String destPhoneNumber;
    private String serviceType;
    private long amount;
    private String pan;
    private ClientCardAuthorizeParams cardAuthorizeParams;

    public ClientCardChargeRequest() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getDestPhoneNumber() {
        return destPhoneNumber;
    }

    public void setDestPhoneNumber(String destPhoneNumber) {
        this.destPhoneNumber = destPhoneNumber;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
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

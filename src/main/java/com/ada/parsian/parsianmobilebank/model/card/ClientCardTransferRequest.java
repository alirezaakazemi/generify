package com.ada.parsian.parsianmobilebank.model.card;

public class ClientCardTransferRequest implements IClientCardRequest {

    private long amount;
    private String destinationPan;
    private String pan;
    private String transactionNumber;
    private ClientCardAuthorizeParams cardAuthorizeParams;

    public ClientCardTransferRequest() {
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
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

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public ClientCardAuthorizeParams getCardAuthorizeParams() {
        return cardAuthorizeParams;
    }

    public void setCardAuthorizeParams(ClientCardAuthorizeParams cardAuthorizeParams) {
        this.cardAuthorizeParams = cardAuthorizeParams;
    }
}

package com.ada.parsian.parsianmobilebank.client.card.model;

public class BankCardPaymentResponse implements IBankCardResponse {

    private Deposit amountBean;
    private String switchResponseRRN;
    private String referenceNumber;

    public BankCardPaymentResponse() {
    }

    public Deposit getAmountBean() {
        return amountBean;
    }

    public void setAmountBean(Deposit amountBean) {
        this.amountBean = amountBean;
    }

    public String getSwitchResponseRRN() {
        return switchResponseRRN;
    }

    public void setSwitchResponseRRN(String switchResponseRRN) {
        this.switchResponseRRN = switchResponseRRN;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }
}

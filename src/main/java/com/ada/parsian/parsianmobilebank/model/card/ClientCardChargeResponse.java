package com.ada.parsian.parsianmobilebank.model.card;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientCardChargeResponse implements IClientCardResponse {

    private long availableBalance;
    private String chargeStatus;
    private Long transactionDate;
    private String transactionNumber;

    public ClientCardChargeResponse() {
    }

    @JsonProperty("balance")
    public long getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(long availableBalance) {
        this.availableBalance = availableBalance;
    }

    @JsonProperty("charge_status")
    public String getChargeStatus() {
        return chargeStatus;
    }

    public void setChargeStatus(String chargeStatus) {
        this.chargeStatus = chargeStatus;
    }

    public Long getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Long transactionDate) {
        this.transactionDate = transactionDate;
    }

    @JsonProperty("tracker_id")
    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }
}
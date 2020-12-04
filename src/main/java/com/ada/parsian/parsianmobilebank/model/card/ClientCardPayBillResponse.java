package com.ada.parsian.parsianmobilebank.model.card;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientCardPayBillResponse implements IClientCardResponse {

    private long availableBalance;
    private long transactionDate;
    private String billType;

    public ClientCardPayBillResponse() {
    }

    @JsonProperty("balance")
    public long getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(long availableBalance) {
        this.availableBalance = availableBalance;
    }

    @JsonProperty("pay_date")
    public long getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(long transactionDate) {
        this.transactionDate = transactionDate;
    }

    @JsonProperty("referral_number")
    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }
}

package com.generify.model.card;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientOwnerResponse implements IClientCardResponse {

    private String name;
    private String bankName;
    private String transactionNumber;

    public ClientOwnerResponse() {
    }

    @JsonProperty("owner_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("bank_name")
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }
}

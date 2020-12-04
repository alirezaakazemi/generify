package com.ada.parsian.parsianmobilebank.model.card;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientCardTransferResponse implements IClientCardResponse {

    private long availableBalance;
    private long ledgerBalance;
    private String transactionNumber;
    private long transactionDate;

    public ClientCardTransferResponse() {
    }

    @JsonProperty("available_balance")
    public long getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(long availableBalance) {
        this.availableBalance = availableBalance;
    }

    @JsonProperty("ledger_balance")
    public long getLedgerBalance() {
        return ledgerBalance;
    }

    public void setLedgerBalance(long ledgerBalance) {
        this.ledgerBalance = ledgerBalance;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    @JsonProperty("switch_response_rrn")
    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public long getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(long transactionDate) {
        this.transactionDate = transactionDate;
    }
}

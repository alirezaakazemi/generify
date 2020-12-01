package com.ada.parsian.parsianmobilebank.model.card;

public class ClientCardTransferResponse implements IClientCardResponse {

    private long availableBalance;
    private long ledgerBalance;
    private String currency;
    private String transactionNumber;
    private long transactionDate;

    public ClientCardTransferResponse() {
    }

    public long getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(long availableBalance) {
        this.availableBalance = availableBalance;
    }

    public long getLedgerBalance() {
        return ledgerBalance;
    }

    public void setLedgerBalance(long ledgerBalance) {
        this.ledgerBalance = ledgerBalance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

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

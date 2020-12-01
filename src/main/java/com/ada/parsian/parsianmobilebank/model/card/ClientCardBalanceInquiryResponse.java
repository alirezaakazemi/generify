package com.ada.parsian.parsianmobilebank.model.card;

public class ClientCardBalanceInquiryResponse implements IClientCardResponse {

    private long availableBalance;
    private long ledgerBalance;
    private String currency;

    public ClientCardBalanceInquiryResponse() {
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
}

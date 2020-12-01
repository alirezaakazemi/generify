package com.ada.parsian.parsianmobilebank.client.card.model;

public class BankCardBalanceInquiryResponse implements IBankCardResponse {

    private AvailableBalance availableBalance;
    private LedgerBalance ledgerBalance;

    public BankCardBalanceInquiryResponse() {
    }

    public AvailableBalance getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(AvailableBalance availableBalance) {
        this.availableBalance = availableBalance;
    }

    public LedgerBalance getLedgerBalance() {
        return ledgerBalance;
    }

    public void setLedgerBalance(LedgerBalance ledgerBalance) {
        this.ledgerBalance = ledgerBalance;
    }
}

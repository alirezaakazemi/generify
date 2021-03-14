package com.generify.client.card.model;

public class Deposit {

    private AvailableBalance availableBalance;
    private LedgerBalance ledgerBalance;

    public Deposit() {
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

package com.ada.parsian.parsianmobilebank.model.card;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientCardBalanceInquiryResponse implements IClientCardResponse {

    private long availableBalance;
    private long ledgerBalance;

    public ClientCardBalanceInquiryResponse() {
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

}

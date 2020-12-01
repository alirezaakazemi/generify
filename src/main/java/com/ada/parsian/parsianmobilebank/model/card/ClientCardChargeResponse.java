package com.ada.parsian.parsianmobilebank.model.card;

public class ClientCardChargeResponse implements IClientCardResponse {

    private long availableBalance;
    private long ledgerBalance;
    private String currency;
    private String productId;
    private String destPhoneNumber;
    private long amount;
    private Long transactionDate;
    private String transactionNumber;

    public ClientCardChargeResponse() {
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getDestPhoneNumber() {
        return destPhoneNumber;
    }

    public void setDestPhoneNumber(String destPhoneNumber) {
        this.destPhoneNumber = destPhoneNumber;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public Long getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Long transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }
}
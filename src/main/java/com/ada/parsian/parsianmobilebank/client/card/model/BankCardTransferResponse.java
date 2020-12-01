package com.ada.parsian.parsianmobilebank.client.card.model;

public class BankCardTransferResponse implements IBankCardResponse {

    private Deposit deposit;
    private String switchResponseRRN;
    private String transactionNumber;
    private String transactionDate;

    public BankCardTransferResponse() {
    }

    public Deposit getDeposit() {
        return deposit;
    }

    public void setDeposit(Deposit deposit) {
        this.deposit = deposit;
    }

    public String getSwitchResponseRRN() {
        return switchResponseRRN;
    }

    public void setSwitchResponseRRN(String switchResponseRRN) {
        this.switchResponseRRN = switchResponseRRN;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

}

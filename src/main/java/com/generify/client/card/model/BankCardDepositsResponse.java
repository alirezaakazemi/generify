package com.generify.client.card.model;

import java.util.List;

public class BankCardDepositsResponse implements IBankCardResponse {
    private int totalRecord;
    private List<BankDeposit> deposits;

    public BankCardDepositsResponse() {
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public List<BankDeposit> getDeposits() {
        return deposits;
    }

    public void setDeposits(List<BankDeposit> bankDeposits) {
        this.deposits = bankDeposits;
    }

    public static class BankDeposit {

        private String cardDepositType;
        private String depositNumber;

        public BankDeposit() {
        }

        public String getCardDepositType() {
            return cardDepositType;
        }

        public void setCardDepositType(String cardDepositType) {
            this.cardDepositType = cardDepositType;
        }

        public String getDepositNumber() {
            return depositNumber;
        }

        public void setDepositNumber(String depositNumber) {
            this.depositNumber = depositNumber;
        }
    }
}

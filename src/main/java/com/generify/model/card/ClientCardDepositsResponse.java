package com.generify.model.card;

import com.generify.client.card.model.BankCardDepositsResponse;
import com.generify.model.DepositType;

import java.util.ArrayList;
import java.util.List;

public class ClientCardDepositsResponse implements IClientCardResponse {

    private int totalRecord;
    private List<Deposit> deposits;

    public ClientCardDepositsResponse() {
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public List<Deposit> getDeposits() {
        return deposits;
    }

    public void setDeposits(List<Deposit> deposits) {
        this.deposits = deposits;
    }

    public void setBankDeposit(List<BankCardDepositsResponse.BankDeposit> bankDeposits) {

        deposits = new ArrayList<>(bankDeposits.size());
        for (BankCardDepositsResponse.BankDeposit bankDeposit : bankDeposits) {
            deposits.add(new Deposit(DepositType.getValue(bankDeposit.getCardDepositType()), bankDeposit.getDepositNumber()));
        }
    }

    static class Deposit {

        private byte cardDepositType;
        private String depositNumber;

        public Deposit() {
        }

        public Deposit(byte cardDepositType, String depositNumber) {
            this.cardDepositType = cardDepositType;
            this.depositNumber = depositNumber;
        }

        public byte getCardDepositType() {
            return cardDepositType;
        }

        public void setCardDepositType(byte cardDepositType) {
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

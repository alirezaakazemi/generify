package com.generify.client.card.model;

import java.util.List;

public class BankCardStatementInquiryResponse implements IBankCardResponse {

    private List<CardStatement> cardStatements;

    public BankCardStatementInquiryResponse() {
    }

    public List<CardStatement> getCardStatements() {
        return cardStatements;
    }

    public void setCardStatements(List<CardStatement> cardStatements) {
        this.cardStatements = cardStatements;
    }

    public static class CardStatement {

        private long amount;
        private long balance;
        private String date;
        private String description;
        private String documentSerial;

        public CardStatement() {
        }

        public long getAmount() {
            return amount;
        }

        public void setAmount(long amount) {
            this.amount = amount;
        }

        public long getBalance() {
            return balance;
        }

        public void setBalance(long balance) {
            this.balance = balance;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDocumentSerial() {
            return documentSerial;
        }

        public void setDocumentSerial(String documentSerial) {
            this.documentSerial = documentSerial;
        }
    }
}

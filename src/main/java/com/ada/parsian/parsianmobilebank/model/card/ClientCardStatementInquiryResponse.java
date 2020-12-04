package com.ada.parsian.parsianmobilebank.model.card;

import com.ada.parsian.parsianmobilebank.Util.ParsianUtil;
import com.ada.parsian.parsianmobilebank.client.card.model.BankCardStatementInquiryResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ClientCardStatementInquiryResponse implements IClientCardResponse {

    private List<ClientCardStatement> clientCardStatementList;

    public ClientCardStatementInquiryResponse(List<BankCardStatementInquiryResponse.CardStatement> cardStatements) {

        clientCardStatementList = new ArrayList<>(cardStatements.size());

        for (BankCardStatementInquiryResponse.CardStatement cardStatement : cardStatements) {

            ClientCardStatement clientCardStatement = new ClientCardStatement();
            clientCardStatement.setAmount(cardStatement.getAmount());
            clientCardStatement.setBalance(cardStatement.getBalance());
            clientCardStatement.setDate(ParsianUtil.getDateTimeFromPattern(cardStatement.getDate()).getTime());
            clientCardStatement.setDescription(cardStatement.getDescription());

            clientCardStatementList.add(clientCardStatement);
        }
    }

    @JsonProperty("statement_bean_clients")
    public List<ClientCardStatement> getCardStatementList() {
        return clientCardStatementList;
    }

    public void setCardStatementList(List<ClientCardStatement> clientCardStatementList) {
        this.clientCardStatementList = clientCardStatementList;
    }

    public static class ClientCardStatement {

        private long amount;
        private long balance;
        private long date;
        private String description;

        public ClientCardStatement() {
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

        public long getDate() {
            return date;
        }

        public void setDate(long date) {
            this.date = date;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}

package com.ada.parsian.parsianmobilebank.client.card.model;

import java.util.List;

public class BankGetCardsResponse implements IBankCardResponse {

    private List<CardBean> cardBeans;

    public BankGetCardsResponse() {
    }

    public List<CardBean> getCardBeans() {
        return cardBeans;
    }

    public void setCardBeans(List<CardBean> cardBeans) {
        this.cardBeans = cardBeans;
    }

}

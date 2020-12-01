package com.ada.parsian.parsianmobilebank.model;

public enum CardType {

    DEBIT, CREDIT, WALLET, PREPAID;

    public String getType() {
        return name();
    }

    public CardType getType(String Type) {

        for (CardType cardStatusType : CardType.values()) {
            if (cardStatusType.getType().equals(Type)) {
                return cardStatusType;
            }
        }

        return null;
    }
}

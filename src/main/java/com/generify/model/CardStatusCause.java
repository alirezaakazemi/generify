package com.generify.model;

public enum CardStatusCause {

    OK, STOLEN_CARD, LOST_CARD, ARBITRATION_FIAT, REPLICATED_CARD, EXPIRED_CARD, PIN_TRYIES_EXCCEDED, REPLICATED_CARD_CAPTURED, OTHER;

    public String getCause() {
        return name();
    }

    public CardStatusCause getCause(String Cause) {

        for (CardStatusCause cardStatusCause : CardStatusCause.values()) {
            if (cardStatusCause.getCause().equals(Cause)) {
                return cardStatusCause;
            }
        }

        return null;
    }
}

package com.generify.model;

public enum CardStatus {

    OK, HOT, WARM, BLOCKED, CAPTURED, EXPIRED, INACTIVE, SETTLEMENT, CLOSED, PRE_ACTIVE;

    public String getStatus() {
        return name();
    }

    public CardStatus getStatus(String status) {

        for (CardStatus cardStatus : CardStatus.values()) {
            if (cardStatus.getStatus().equals(status)) {
                return cardStatus;
            }
        }

        return null;
    }
}

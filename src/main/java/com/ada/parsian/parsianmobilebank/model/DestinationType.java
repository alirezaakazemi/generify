package com.ada.parsian.parsianmobilebank.model;

public enum DestinationType {

    DEPOSIT(1), CARD(2), IBAN(3);

    private final byte value;

    DestinationType(int value) {
        this.value = (byte) value;
    }

    public byte getValue() {
        return value;
    }

    public DestinationType getType(byte type) {

        for (DestinationType destinationType : DestinationType.values()) {
            if (destinationType.value == type) {
                return destinationType;
            }
        }

        return null;
    }
}

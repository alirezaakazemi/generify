package com.generify.model;

public enum TransferDestinationType {

    DEPOSIT, PAN;

    public String getValue() {
        return name();
    }

    public TransferDestinationType getType(String type) {

        for (TransferDestinationType transferDestinationType : TransferDestinationType.values()) {
            if (transferDestinationType.getValue().equals(type)) {
                return transferDestinationType;
            }
        }

        return null;
    }
}

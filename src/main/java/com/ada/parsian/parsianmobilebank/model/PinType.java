package com.ada.parsian.parsianmobilebank.model;

public enum PinType {

    EPAY;

    public String value() {
        return name();
    }

    public static PinType fromValue(String v) {
        return valueOf(v);
    }

    public PinType getType(String type) {

        for (PinType pinType : PinType.values()) {
            if (pinType.value().equals(type)) {
                return pinType;
            }
        }
        return null;
    }
}

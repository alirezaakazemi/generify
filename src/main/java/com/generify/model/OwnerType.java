package com.generify.model;

public enum OwnerType {

    CARD, DEPOSIT, IBAN, LOAN;

    public String getValue() {
        return name();
    }

    public static OwnerType fromValue(String v) {
        return valueOf(v);
    }

    public OwnerType getType(String type) {

        for (OwnerType ownerType : OwnerType.values()) {
            if (ownerType.getValue().equals(type)) {
                return ownerType;
            }
        }
        return null;
    }
}

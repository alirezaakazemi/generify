package com.ada.parsian.parsianmobilebank.model;

public enum BillType {

    WATER, ELECTRICITY, GAS, IMMOBILE_PHONE, MOBILE_PHONE, MUNICIPALITY_DUE, UNKNOWN, CUSTOM, MUNICIPALITY_7, TAX;

    public String getValue() {
        return name();
    }

    public static BillType fromValue(String v) {
        return valueOf(v);
    }

    public BillType getType(String type) {

        for (BillType billType : BillType.values()) {
            if (billType.getValue().equals(type)) {
                return billType;
            }
        }
        return null;
    }
}

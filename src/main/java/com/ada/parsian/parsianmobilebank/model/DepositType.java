package com.ada.parsian.parsianmobilebank.model;

public enum DepositType {

    MAIN(1), SECONDARY(2);

    private final byte value;

    DepositType(int value) {
        this.value = (byte) value;
    }

    public byte getValue() {
        return value;
    }

    public DepositType getType(byte type) {

        for (DepositType depositType : DepositType.values()) {
            if (depositType.value == type) {
                return depositType;
            }
        }

        return null;
    }

    public static byte getValue(String bankDepositType) {
        if (bankDepositType.equals(Constant.DEPOSIT_MAIN_TYPE)) {
            return MAIN.value;
        } else {
            return SECONDARY.value;
        }
    }
}

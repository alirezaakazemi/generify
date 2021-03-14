package com.generify.model;

public enum ChargeReverseStatus {

    SENT(0), SUCCESS(1), FAIL(2), ERROR(3);

    private final byte value;

    ChargeReverseStatus(int value) {
        this.value = (byte) value;
    }

    public byte getValue() {
        return value;
    }

    public ChargeReverseStatus getStatus(byte status) {

        for (ChargeReverseStatus chargeStatus : ChargeReverseStatus.values()) {
            if (chargeStatus.value == status) {
                return chargeStatus;
            }
        }

        return null;
    }
}

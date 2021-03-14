package com.generify.model;

public enum ChargeReserveStatus {

    SENT(0), SUCCESS(1), FAIL(2), ERROR(3);

    private final byte value;

    ChargeReserveStatus(int value) {
        this.value = (byte) value;
    }

    public byte getValue() {
        return value;
    }

    public ChargeReserveStatus getStatus(byte status) {

        for (ChargeReserveStatus chargeStatus : ChargeReserveStatus.values()) {
            if (chargeStatus.value == status) {
                return chargeStatus;
            }
        }

        return null;
    }
}

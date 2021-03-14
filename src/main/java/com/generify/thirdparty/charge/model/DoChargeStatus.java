package com.generify.thirdparty.charge.model;

public enum DoChargeStatus {

    REQUEST_REGISTERED(1), FAILED(3);

    private final byte value;

    DoChargeStatus(int value) {
        this.value = (byte) value;
    }

    public byte getValue() {
        return value;
    }
}

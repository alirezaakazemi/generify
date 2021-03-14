package com.generify.model;

public enum TransactionStatus {

    SUCCESS(1), FAIL(2), ERROR(3);

    private final byte value;

    TransactionStatus(int value) {
        this.value = (byte) value;
    }

    public byte getValue() {
        return value;
    }

    public TransactionStatus getStatus(byte status) {

        for (TransactionStatus transactionStatus : TransactionStatus.values()) {
            if (transactionStatus.value == status) {
                return transactionStatus;
            }
        }

        return null;
    }
}

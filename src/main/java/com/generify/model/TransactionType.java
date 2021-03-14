package com.generify.model;

public enum TransactionType {

    CARD_TRANSFER(1), PAY_BILL(2), CHARGE(3), CARD_OWNER(4), CARD_BALANCE(5),
    CARD_STATEMENT(6), HOT_CARD(7), LOGIN(7), CARD_DEPOSITS(8), GET_CARDS(9);

    private final byte value;

    TransactionType(int value) {
        this.value = (byte) value;
    }

    public byte getValue() {
        return value;
    }

    public TransactionType getType(byte type) {

        for (TransactionType transactionType : TransactionType.values()) {
            if (transactionType.value == type) {
                return transactionType;
            }
        }

        return null;
    }
}

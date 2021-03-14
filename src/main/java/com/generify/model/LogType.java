package com.generify.model;

public enum LogType {

    CLIENT_REQUEST(1), CLIENT_RESPONSE(2), BANK_REQUEST(3), BANK_RESPONSE(4), THIRD_PARTY_SERVER_REQUEST(5), THIRD_PARTY_SERVER_RESPONSE(6);

    private final byte value;

    LogType(int value) {
        this.value = (byte) value;
    }

    public byte getValue() {
        return value;
    }

    public LogType getType(byte type) {

        for (LogType logType : LogType.values()) {
            if (logType.value == type) {
                return logType;
            }
        }

        return null;
    }
}

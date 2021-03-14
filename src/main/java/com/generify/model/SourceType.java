package com.generify.model;

public enum SourceType {

    DEPOSIT((byte) 1), CARD((byte) 2);

    private final Byte value;

    SourceType(Byte value) {
        this.value = value;
    }

    public Byte getValue() {
        return value;
    }

    public SourceType getType(Byte type) {

        for (SourceType sourceType : SourceType.values()) {
            if (sourceType.value.equals(type)) {
                return sourceType;
            }
        }

        return null;
    }
}

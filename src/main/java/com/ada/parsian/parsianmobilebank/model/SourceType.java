package com.ada.parsian.parsianmobilebank.model;

public enum SourceType {

    DEPOSIT((long) 1), CARD((long) 2);

    private final Long value;

    SourceType(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    public SourceType getType(Long type) {

        for (SourceType sourceType : SourceType.values()) {
            if (sourceType.value.equals(type)) {
                return sourceType;
            }
        }

        return null;
    }
}

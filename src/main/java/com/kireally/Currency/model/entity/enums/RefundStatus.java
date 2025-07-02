package com.kireally.Currency.model.entity.enums;

import lombok.Getter;

@Getter
public enum RefundStatus {
    COMPLETED,
    FAILED;

    public static RefundStatus fromString(String value) {
        for (RefundStatus status : RefundStatus.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}

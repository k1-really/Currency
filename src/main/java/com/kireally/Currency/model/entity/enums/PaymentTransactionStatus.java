package com.kireally.Currency.model.entity.enums;

import lombok.Getter;

@Getter
public enum PaymentTransactionStatus {
    PROCESSING,
    SUCCESS,
    FAILED;

    public static PaymentTransactionStatus fromString(String value) {
        for (PaymentTransactionStatus status : PaymentTransactionStatus.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}

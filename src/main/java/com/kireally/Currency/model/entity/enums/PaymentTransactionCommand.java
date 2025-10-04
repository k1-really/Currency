package com.kireally.Currency.model.entity.enums;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum PaymentTransactionCommand {
    CREATE,
    REFUND,
    UNKNOWN;

    public static PaymentTransactionCommand fromString(String command) {
        try {
            return valueOf(command.toUpperCase());
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}

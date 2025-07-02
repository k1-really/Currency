package com.kireally.Currency.exception;

import lombok.Getter;

@Getter
public class InsufficientFundsException extends RuntimeException {
    private final Long accountId;

    public InsufficientFundsException(Long accountId) {
        super("Insufficient funds on currency account: " + accountId);
        this.accountId = accountId;
    }
}

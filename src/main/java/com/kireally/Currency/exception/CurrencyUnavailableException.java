package com.kireally.Currency.exception;

public class CurrencyUnavailableException extends RuntimeException {
    public CurrencyUnavailableException(String message) {
        super(message);
    }
}

package com.kireally.Currency.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
public class PaymentTransactionValidationException extends RuntimeException{
    private final List<String> errors;

    public PaymentTransactionValidationException(List<String> errors) {
        super("Transaction validation failed");
        this.errors = errors;
    }

}


package com.kireally.Currency.service.handler;

public interface PaymentTransactionCommandHandler {
    void processCommand(String requestId, String message);
}

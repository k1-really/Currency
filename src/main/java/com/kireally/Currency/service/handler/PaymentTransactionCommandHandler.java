package com.kireally.Currency.service.handler;

public interface PaymentTransactionCommandHandler {
    void processCommand(Long requestId, String message);
}

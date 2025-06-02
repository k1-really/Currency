package com.kireally.Currency.service.handler;

public interface PaymentTransactionCommandHandler {
    void process(Long requestId, String message);
}

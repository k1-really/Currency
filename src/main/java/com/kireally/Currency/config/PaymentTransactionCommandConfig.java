package com.kireally.Currency.config;

import com.kireally.Currency.model.entity.enums.PaymentTransactionCommand;
import com.kireally.Currency.service.handler.CancelPaymentTransactionHandler;
import com.kireally.Currency.service.handler.CreatePaymentTransactionHandler;
import com.kireally.Currency.service.handler.PaymentTransactionCommandHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class PaymentTransactionCommandConfig {
    @Bean
    public Map<PaymentTransactionCommand, PaymentTransactionCommandHandler> commandHandlers(
            CreatePaymentTransactionHandler createPaymentTransactionHandler,
            CancelPaymentTransactionHandler cancelPaymentTransactionHandler
    ) {
        Map<PaymentTransactionCommand, PaymentTransactionCommandHandler> commandHandlers = new HashMap<>();
        commandHandlers.put(PaymentTransactionCommand.CREATE, createPaymentTransactionHandler);
        commandHandlers.put(PaymentTransactionCommand.REFUND, cancelPaymentTransactionHandler);
        return commandHandlers;
    }
}

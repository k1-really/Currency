package com.kireally.Currency.controller.kafka.consumer;

import com.kireally.Currency.model.entity.enums.PaymentTransactionCommand;
import com.kireally.Currency.service.handler.PaymentTransactionCommandHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentTransactionCommandListener {
    private final Map<PaymentTransactionCommand, PaymentTransactionCommandHandler> commandHandlers;

    @KafkaListener(topics = "payment-command", containerFactory = "kafkaListenerContainerFactory")
    public void consumeCommand(ConsumerRecord<String, String> record) {
        log.info("Payment command received, command:{}", record);

        PaymentTransactionCommand command = extractCommand(record);
        if (command.equals(PaymentTransactionCommand.UNKNOWN)) {
            throw new IllegalArgumentException("Unknown command");
        }
        String key = record.key();

        commandHandlers.get(command).processCommand(
                key, record.value()
        );
    }

    private PaymentTransactionCommand extractCommand(ConsumerRecord<String, String> record) {
        Header header = record.headers().lastHeader("command");
        if (header != null) {
            return PaymentTransactionCommand.fromString(new String(header.value(), StandardCharsets.UTF_8));
        }
        return PaymentTransactionCommand.UNKNOWN;
    }
}
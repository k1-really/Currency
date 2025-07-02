package com.kireally.Currency.controller.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kireally.Currency.model.entity.enums.PaymentTransactionCommand;
import com.kireally.Currency.model.entity.enums.PaymentTransactionStatus;
import com.kireally.Currency.service.handler.PaymentTransactionCommandHandler;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
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

        var comand = extractCommand(record);
        if (comand.equals(PaymentTransactionCommand.UNKNOWN)) {
            throw new IllegalArgumentException("Unknown command");
        }
        Long key = Long.valueOf(record.key());

        commandHandlers.get(comand).processCommand(
                key, record.value()
        );
    }

    private PaymentTransactionCommand extractCommand(ConsumerRecord<String, String> record) {
        var header = record.headers().lastHeader("command");
        if (header != null) {
            return PaymentTransactionCommand.fromString(new String(header.value(), StandardCharsets.UTF_8));
        }
        return PaymentTransactionCommand.UNKNOWN;
    }
}
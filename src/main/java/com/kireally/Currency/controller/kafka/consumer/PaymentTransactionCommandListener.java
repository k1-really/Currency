package com.kireally.Currency.controller.kafka.consumer;

import com.kireally.Currency.model.entity.enums.PaymentTransactionCommand;
import com.kireally.Currency.model.entity.enums.PaymentTransactionStatus;
import com.kireally.Currency.service.handler.PaymentTransactionCommandHandler;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentTransactionCommandListener {
    private final Map<PaymentTransactionCommand, PaymentTransactionCommandHandler> commandHandlers;
    @KafkaListener(topics = "payment-command", containerFactory = "kafkaListenerContainerFactory")
    public void consumePaymentTransactionCommand(ConsumerRecord<String, String> record){
        log.info("Payment command received, command:{}", record);

        if (getPaymentTransactionCommand(record).equals(PaymentTransactionCommand.UNKNOWN)) {
            throw new IllegalArgumentException("Unknown command");
        }

        commandHandlers.get(getPaymentTransactionCommand(record)).process(Long.valueOf(record.key()), record.value());

    }

    private PaymentTransactionCommand getPaymentTransactionCommand(ConsumerRecord<String, String> record){
        var commonHeader = record.headers().lastHeader("command");
        if(commonHeader != null){
            return PaymentTransactionCommand.fromString(new String(commonHeader.value()));
        }
    return PaymentTransactionCommand.UNKNOWN;
    }
}

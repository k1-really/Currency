package com.kireally.Currency.service.handler;

import com.kireally.Currency.controller.kafka.producer.PaymentTransactionProducer;
import com.kireally.Currency.model.dto.paymentTransaction.CancelPaymentRequest;
import com.kireally.Currency.model.entity.enums.PaymentTransactionCommand;
import com.kireally.Currency.service.RefundService;
import com.kireally.Currency.service.impl.RefundServiceImpl;
import com.kireally.Currency.util.JsonConverter;
import com.kireally.Currency.validation.validators.PaymentTransactionValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
@Slf4j
@Component
@RequiredArgsConstructor
public class RefundPaymentTransactionHandler implements PaymentTransactionCommandHandler {
    private final JsonConverter jsonConverter;
    private final PaymentTransactionValidator paymentTransactionValidator;
    private final RefundServiceImpl refundService;
    private final PaymentTransactionProducer paymentTransactionProducer;

    @Override
    public void processCommand(Long requestId, String message) {
        var request = jsonConverter.fromJson(message, CancelPaymentRequest.class);
        paymentTransactionValidator.validateCancelTransactionRequest(request);
        var result = refundService.cancelPayment(request);

        paymentTransactionProducer.sendCommandResult(requestId, PaymentTransactionCommand.REFUND, result.toString());
    }
}

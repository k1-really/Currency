package com.kireally.Currency.service.handler;

import com.kireally.Currency.controller.kafka.producer.PaymentTransactionProducer;
import com.kireally.Currency.model.dto.paymentTransaction.CancelPaymentRequest;
import com.kireally.Currency.model.entity.bankAccount.BankAccount;
import com.kireally.Currency.model.entity.bankAccount.PaymentTransaction;
import com.kireally.Currency.model.entity.enums.PaymentTransactionCommand;
import com.kireally.Currency.service.BankAccountService;
import com.kireally.Currency.service.PaymentTransactionService;
import com.kireally.Currency.service.RefundService;
import com.kireally.Currency.util.JsonConverter;
import com.kireally.Currency.validation.validators.PaymentTransactionValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class CancelPaymentTransactionHandler implements PaymentTransactionCommandHandler{
    private final JsonConverter jsonConverter;
    private final PaymentTransactionValidator paymentTransactionValidator;
    private final RefundService refundService;
    private final PaymentTransactionProducer paymentTransactionProducer;


    @Override
    public void process(Long requestId, String message) {

            var request = jsonConverter.fromJson(message, CancelPaymentRequest.class);
            paymentTransactionValidator.validateCancelTransactionRequest(request);
            var result = refundService.cancelPayment(request);

            paymentTransactionProducer.sendCommandResult(requestId, PaymentTransactionCommand.REFUND, result.toString());
        }

}

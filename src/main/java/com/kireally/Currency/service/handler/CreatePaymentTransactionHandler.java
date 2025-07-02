package com.kireally.Currency.service.handler;

import com.kireally.Currency.controller.kafka.producer.PaymentTransactionProducer;
import com.kireally.Currency.mapper.PaymentTransactionMapper;
import com.kireally.Currency.model.dto.paymentTransaction.CreatePaymentTransactionRequest;
import com.kireally.Currency.model.entity.bankAccount.BankAccount;
import com.kireally.Currency.model.entity.bankAccount.PaymentTransaction;
import com.kireally.Currency.model.entity.enums.PaymentTransactionCommand;
import com.kireally.Currency.model.entity.enums.PaymentTransactionStatus;
import com.kireally.Currency.service.BankAccountService;
import com.kireally.Currency.service.PaymentTransactionService;
import com.kireally.Currency.service.impl.PaymentTransactionServiceImpl;
import com.kireally.Currency.util.JsonConverter;
import com.kireally.Currency.validation.validators.PaymentTransactionValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
/**
 * Обрабатывает команды на возврат платежа
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CreatePaymentTransactionHandler implements PaymentTransactionCommandHandler {
    private final PaymentTransactionServiceImpl paymentTransactionService;

    private final JsonConverter jsonConverter;
    private final PaymentTransactionValidator paymentTransactionValidator;
    private final PaymentTransactionProducer paymentTransactionProducer;

    /**
     * Переводит {@code amount} со счёта source на счет dest,
     * с конвертацией по курсу, если currency отличаются.
     */
    @Override
    public void processCommand(Long requestId, String message) {
        var request = jsonConverter.fromJson(message, CreatePaymentTransactionRequest.class);
        paymentTransactionValidator.validateCreateTransactionRequest(request);

        var tx = paymentTransactionService.transfer(request);

        paymentTransactionProducer.sendCommandResult(
                requestId,
                PaymentTransactionCommand.CREATE,
                tx.toString()
        );
    }
}


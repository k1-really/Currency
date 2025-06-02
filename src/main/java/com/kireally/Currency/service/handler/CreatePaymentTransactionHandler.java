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
import com.kireally.Currency.util.JsonConverter;
import com.kireally.Currency.validation.validators.PaymentTransactionValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreatePaymentTransactionHandler implements PaymentTransactionCommandHandler{
    private final JsonConverter jsonConverter;
    private final PaymentTransactionValidator paymentTransactionValidator;
    private final BankAccountService bankAccountService;
    private final PaymentTransactionMapper paymentTransactionMapper;
    private final PaymentTransactionService paymentTransactionService;
    private final PaymentTransactionProducer paymentTransactionProducer;

    @Override
    @Transactional
    public void process(Long requestId, String message) {
        CreatePaymentTransactionRequest request = jsonConverter.fromJson(message, CreatePaymentTransactionRequest.class);
        paymentTransactionValidator.validateCreatePaymentTransactionRequest(request);
        BankAccount sourceBankAccount = bankAccountService.findById(request.getSourceBankAccountId()).get();
        sourceBankAccount.setBalance(
                sourceBankAccount.getBalance().subtract(request.getAmount())
        );
        Optional<BankAccount> destinationBankAccount = Optional.empty();
        if(request.getDestinationBankAccountId() != null){
            destinationBankAccount = bankAccountService.findById(request.getDestinationBankAccountId());
            destinationBankAccount.get().setBalance(
                    destinationBankAccount.get().getBalance().add(request.getAmount())
            );
        }

        PaymentTransaction paymentTransaction = paymentTransactionMapper.toEntity(request);
        paymentTransaction.setSourceBankAccount(sourceBankAccount);
        destinationBankAccount.ifPresent(paymentTransaction::setDestinationBankAccount);
        paymentTransaction.setPaymentTransactionStatus(PaymentTransactionStatus.SUCCESS);
        PaymentTransaction savedPaymentTransaction = paymentTransactionService.save(paymentTransaction);
        paymentTransactionProducer.sendCommandResult(
                requestId,
                PaymentTransactionCommand.CREATE,
                savedPaymentTransaction.toString()
        );
    }
}

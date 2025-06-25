package com.kireally.Currency.service.impl;

import com.kireally.Currency.mapper.PaymentTransactionMapper;
import com.kireally.Currency.model.dto.paymentTransaction.CreatePaymentTransactionRequest;
import com.kireally.Currency.model.dto.paymentTransaction.CreatePaymentTransactionResponse;
import com.kireally.Currency.model.entity.bankAccount.PaymentTransaction;
import com.kireally.Currency.repository.PaymentTransactionRepository;
import com.kireally.Currency.service.PaymentTransactionService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.PaymentTransactionResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentTransactionServiceImpl implements PaymentTransactionService {
    private final PaymentTransactionRepository paymentTransactionRepository;
    private final PaymentTransactionMapper paymentTransactionMapper;

    @Transactional
    public CreatePaymentTransactionResponse save(PaymentTransaction paymentTransaction) {
        var entity = paymentTransactionRepository.save(paymentTransaction);
        return paymentTransactionMapper.toKafkaDto(entity);
    }

    @Transactional
    public Optional<PaymentTransaction> findOptionalById(@NotNull Long id){
        try{
            return paymentTransactionRepository.findById(id);
        } catch (EntityNotFoundException e){
            return Optional.empty();
        }
    }

    @Transactional
    public PaymentTransactionResponse findById(@NotNull Long id){
        var entity = paymentTransactionRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Payment transaction with id " + id + " not found")
        );
        return paymentTransactionMapper.toDto(entity);
    }




}

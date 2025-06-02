package com.kireally.Currency.service.impl;

import com.kireally.Currency.mapper.PaymentTransactionMapper;
import com.kireally.Currency.model.dto.paymentTransaction.CreatePaymentTransactionRequest;
import com.kireally.Currency.model.dto.paymentTransaction.CreatePaymentTransactionResponse;
import com.kireally.Currency.model.entity.bankAccount.PaymentTransaction;
import com.kireally.Currency.repository.PaymentTransactionRepository;
import com.kireally.Currency.service.PaymentTransactionService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public PaymentTransaction save(PaymentTransaction paymentTransaction){

        return paymentTransactionRepository.save(paymentTransaction);
    }

    @Transactional
    public Optional<PaymentTransaction> findById(Long id){
        return paymentTransactionRepository.findById(id);
    }

}

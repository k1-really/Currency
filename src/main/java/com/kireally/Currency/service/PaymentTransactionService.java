package com.kireally.Currency.service;


import com.kireally.Currency.model.dto.paymentTransaction.CreatePaymentTransactionRequest;
import com.kireally.Currency.model.dto.paymentTransaction.CreatePaymentTransactionResponse;
import com.kireally.Currency.model.entity.bankAccount.PaymentTransaction;

import java.util.Optional;

public interface PaymentTransactionService {
    PaymentTransaction save(PaymentTransaction paymentTransaction);
    Optional<PaymentTransaction> findById(Long id);
}

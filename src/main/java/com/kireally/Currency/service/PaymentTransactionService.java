package com.kireally.Currency.service;


import com.kireally.Currency.model.dto.paymentTransaction.CreatePaymentTransactionRequest;
import com.kireally.Currency.model.dto.paymentTransaction.CreatePaymentTransactionResponse;
import com.kireally.Currency.model.entity.bankAccount.PaymentTransaction;
import jakarta.persistence.EntityNotFoundException;
import org.openapitools.model.PaymentTransactionResponse;

import java.util.Optional;

public interface PaymentTransactionService {
    CreatePaymentTransactionResponse save(PaymentTransaction paymentTransaction);
    PaymentTransactionResponse findById(Long id);

    Optional<PaymentTransaction> findOptionalById(Long id);


}

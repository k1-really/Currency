package com.kireally.Currency.service;

import com.kireally.Currency.model.dto.paymentTransaction.CreatePaymentTransactionRequest;
import com.kireally.Currency.model.entity.bankAccount.PaymentTransaction;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface PaymentTransactionService {
    PaymentTransaction save(PaymentTransaction paymentTransaction);
    Optional<PaymentTransaction> findById(@NotNull Long id);
    PaymentTransaction transfer(CreatePaymentTransactionRequest request);


}

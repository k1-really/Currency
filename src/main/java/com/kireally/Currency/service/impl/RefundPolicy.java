package com.kireally.Currency.service.impl;

import com.kireally.Currency.exception.PaymentTransactionValidationException;
import com.kireally.Currency.model.entity.bankAccount.PaymentTransaction;
import com.kireally.Currency.model.entity.bankAccount.Refund;
import com.kireally.Currency.model.entity.enums.PaymentTransactionStatus;
import com.kireally.Currency.service.PaymentTransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ZERO;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefundPolicy {
    private final PaymentTransactionServiceImpl paymentTransactionService;

    public PaymentTransaction checkAndFetchTransaction(Long transactionId) {
        var transaction = paymentTransactionService.findById(transactionId)
                .orElseThrow(() ->
                        new PaymentTransactionValidationException(
                                List.of("Transaction " + transactionId + " not found")));

        if (transaction.getStatus() != PaymentTransactionStatus.SUCCESS) {
            throw new PaymentTransactionValidationException(
                    List.of("Cannot refund transaction with status " + transaction.getStatus()));
        }
        return transaction;
    }

    public BigDecimal calculateRemainingRefundable(PaymentTransaction transaction,
                                                   BigDecimal requestedAmount) {
        BigDecimal already = transaction.getRefunds().stream()
                .map(Refund::getRefundedAmount)
                .reduce(ZERO, BigDecimal::add);

        var remaining = requestedAmount.subtract(already);
        if (requestedAmount.compareTo(remaining) > 0) {
            throw new PaymentTransactionValidationException(
                    List.of("Requested refund exceeds available: " + remaining));
        }
        return remaining;
    }
}

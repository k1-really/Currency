package com.kireally.Currency.service;

import com.kireally.Currency.model.dto.paymentTransaction.CancelPaymentRequest;
import com.kireally.Currency.model.dto.paymentTransaction.CancelPaymentResponse;

import java.math.BigDecimal;

public interface RefundService {
    BigDecimal getTotalRefundedAmount(Long transactionId);
    CancelPaymentResponse cancelPayment(CancelPaymentRequest cancelPaymentRequest);
}
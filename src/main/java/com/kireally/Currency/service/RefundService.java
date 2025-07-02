package com.kireally.Currency.service;

import com.kireally.Currency.model.dto.paymentTransaction.CancelPaymentRequest;
import com.kireally.Currency.model.dto.paymentTransaction.CancelPaymentResponse;

import java.math.BigDecimal;

public interface RefundService {
    CancelPaymentResponse cancelPayment(CancelPaymentRequest request);
}
package com.kireally.Currency.service.impl;

import com.kireally.Currency.mapper.RefundMapper;
import com.kireally.Currency.model.dto.paymentTransaction.CancelPaymentRequest;
import com.kireally.Currency.model.dto.paymentTransaction.CancelPaymentResponse;
import com.kireally.Currency.model.entity.bankAccount.Refund;
import com.kireally.Currency.model.entity.enums.RefundStatus;
import com.kireally.Currency.repository.RefundRepository;
import com.kireally.Currency.service.PaymentTransactionService;
import com.kireally.Currency.service.RefundService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefundServiceImpl {
    private final RefundRepository refundRepository;
    private final RefundCalculator refundCalculator;
    private final RefundPolicy refundPolicy;
    private final RefundMapper refundMapper;

    @Transactional
    public CancelPaymentResponse cancelPayment(CancelPaymentRequest request) {
        var transaction = refundPolicy.checkAndFetchTransaction(request.getTransactionId());
        var remainingAmount = refundPolicy.calculateRemainingRefundable(transaction, request.getRefundedAmount());

        refundCalculator.applyRefund(transaction, remainingAmount);
        var refund = refundMapper.toEntity(request, RefundStatus.COMPLETED);
        refund.setPaymentTransaction(transaction);
        var savedRefund = refundRepository.save(refund);

        return refundMapper.toResponse(savedRefund);
    }
}


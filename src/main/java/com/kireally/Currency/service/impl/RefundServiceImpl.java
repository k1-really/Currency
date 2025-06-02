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
public class RefundServiceImpl implements RefundService {
    private final PaymentTransactionService paymentTransactionService;
    private final RefundRepository refundRepository;
    private final RefundMapper refundMapper;
    public BigDecimal getTotalRefundedAmount(Long transactionId) {
        var refunds = refundRepository.findAllByPaymentTransactionId(transactionId);

        return refunds.stream()
                .map(Refund::getRefundedAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Transactional
    public CancelPaymentResponse cancelPayment(CancelPaymentRequest cancelPaymentRequest) {
        var sourceTransaction = paymentTransactionService.findById(cancelPaymentRequest.getTransactionId()).get();

        sourceTransaction.setAmount(
                sourceTransaction.getAmount().subtract(cancelPaymentRequest.getRefundedAmount())
        );

        var entity = refundRepository.save(
                refundMapper.toEntity(cancelPaymentRequest, RefundStatus.COMPLETED)
        );
        entity.setPaymentTransaction(sourceTransaction);

        return refundMapper.toResponse(entity);
    }

}

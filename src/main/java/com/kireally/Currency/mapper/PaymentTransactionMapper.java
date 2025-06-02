package com.kireally.Currency.mapper;

import com.kireally.Currency.model.dto.paymentTransaction.CreatePaymentTransactionRequest;
import com.kireally.Currency.model.dto.paymentTransaction.CreatePaymentTransactionResponse;
import com.kireally.Currency.model.entity.bankAccount.PaymentTransaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentTransactionMapper {
    PaymentTransaction toEntity(CreatePaymentTransactionRequest createPaymentTransactionRequest);
    CreatePaymentTransactionResponse toResponse(PaymentTransaction paymentTransaction);
}

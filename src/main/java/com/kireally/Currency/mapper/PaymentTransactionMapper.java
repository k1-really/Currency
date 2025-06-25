package com.kireally.Currency.mapper;

import com.kireally.Currency.model.dto.enums.CommandResultStatus;
import com.kireally.Currency.model.dto.paymentTransaction.CreatePaymentTransactionRequest;
import com.kireally.Currency.model.dto.paymentTransaction.CreatePaymentTransactionResponse;
import com.kireally.Currency.model.entity.bankAccount.BankAccount;
import com.kireally.Currency.model.entity.bankAccount.PaymentTransaction;
import com.kireally.Currency.model.entity.enums.PaymentTransactionStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.openapitools.model.PaymentTransactionResponse;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring")
public interface PaymentTransactionMapper {
    @Mapping(source = "id", target ="transactionId")
    @Mapping(source = "createdAt", target = "createdAt", qualifiedByName = "mapLocalDateTimeToOffsetDateTime")
    PaymentTransactionResponse toDto(PaymentTransaction paymentTransaction);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(source = "paymentTransactionRequest.amount", target = "amount")
    @Mapping(source = "paymentTransactionRequest.currency", target = "currency")
    @Mapping(source = "sourceBankAccount", target = "sourceBankAccount")
    @Mapping(source = "destinationBankAccount", target = "destinationBankAccount")
    @Mapping(source = "status", target = "status")
    PaymentTransaction  toEntity(CreatePaymentTransactionRequest paymentTransactionRequest,
                                BankAccount sourceBankAccount,
                                BankAccount destinationBankAccount,
                                PaymentTransactionStatus status);

    @Mapping(source = "status", target = "status", qualifiedByName = "mapPaymentTransactionStatusToCommandResultStatus")
    @Mapping(source = "createdAt", target = "executedAt")
    CreatePaymentTransactionResponse toKafkaDto(PaymentTransaction paymentTransaction);

    @Named("mapLocalDateTimeToOffsetDateTime")
    default OffsetDateTime mapLocalDateTimeToOffsetDateTime(LocalDateTime localDateTime) {
        return (localDateTime == null) ? null : localDateTime.atOffset(ZoneOffset.UTC);
    }

    @Named("mapPaymentTransactionStatusToCommandResultStatus")
    default CommandResultStatus mapPaymentTransactionStatusToCommandStatus(PaymentTransactionStatus paymentTransactionStatus) {
        if (paymentTransactionStatus == null) {
            return CommandResultStatus.FAILED;
        }
        return switch (paymentTransactionStatus) {
            case SUCCESS -> CommandResultStatus.SUCCESS;
            case FAILED, PROCESSING -> CommandResultStatus.FAILED;
        };
    }
}



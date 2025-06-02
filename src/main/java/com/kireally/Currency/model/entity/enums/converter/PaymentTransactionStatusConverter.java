package com.kireally.Currency.model.entity.enums.converter;

import com.kireally.Currency.model.entity.enums.PaymentTransactionStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PaymentTransactionStatusConverter implements AttributeConverter<PaymentTransactionStatus, String> {

    @Override
    public String convertToDatabaseColumn(PaymentTransactionStatus status) {
        return (status == null) ? null : status.name();
    }

    @Override
    public PaymentTransactionStatus convertToEntityAttribute(String dbData) {
        return (dbData == null) ? null : PaymentTransactionStatus.fromString(dbData);
    }
}


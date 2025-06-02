package com.kireally.Currency.model.entity.enums.converter;

import com.kireally.Currency.model.entity.enums.PaymentTransactionStatus;
import com.kireally.Currency.model.entity.enums.RefundStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.Getter;

@Converter(autoApply = true)
public class RefundStatusConverter implements AttributeConverter<RefundStatus,String> {
    @Override
    public String convertToDatabaseColumn(RefundStatus refundStatus) {
        return refundStatus == null ? null : refundStatus.name();
    }

    @Override
    public RefundStatus convertToEntityAttribute(String s) {
        return s == null ? null : RefundStatus.fromString(s);
    }
}

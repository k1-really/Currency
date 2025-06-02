package com.kireally.Currency.mapper;

import com.kireally.Currency.model.dto.enums.CommandResultStatus;
import com.kireally.Currency.model.dto.paymentTransaction.CancelPaymentRequest;
import com.kireally.Currency.model.dto.paymentTransaction.CancelPaymentResponse;
import com.kireally.Currency.model.entity.bankAccount.Refund;
import com.kireally.Currency.model.entity.enums.RefundStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface RefundMapper {

    Refund toEntity(CancelPaymentRequest cancelPaymentRequest,
                    RefundStatus status);

    @Mapping(source = "status", target = "status", qualifiedByName = "mapRefundStatusToCommandStatus")
    CancelPaymentResponse toResponse(Refund refund);

    @Named("mapRefundStatusToCommandStatus")
    default CommandResultStatus mapRefundStatusToCommandStatus(RefundStatus refundStatus) {
        if (refundStatus == null) {
            return CommandResultStatus.FAILED;
        }
        return switch (refundStatus) {
            case COMPLETED -> CommandResultStatus.SUCCESS;
            case FAILED -> CommandResultStatus.FAILED;
        };
    }
}


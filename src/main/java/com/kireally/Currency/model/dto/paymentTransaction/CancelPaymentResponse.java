package com.kireally.Currency.model.dto.paymentTransaction;

import com.kireally.Currency.model.dto.enums.CommandResultStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class CancelPaymentResponse {
    private Long refundId;
    private CommandResultStatus status;
    private String errorMessage;
}

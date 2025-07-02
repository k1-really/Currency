package com.kireally.Currency.model.dto.paymentTransaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CancelPaymentRequest {
    @NotNull(message = "Transaction ID must not be null")
    private Long transactionId;
    @NotNull(message = "Cancel amount must not be null")
    @Min(value = 1, message = "Cancel amount must be greater than zero")
    private BigDecimal refundedAmount;
    private String reason;
}

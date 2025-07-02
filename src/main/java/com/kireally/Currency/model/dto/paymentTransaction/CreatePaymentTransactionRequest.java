package com.kireally.Currency.model.dto.paymentTransaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentTransactionRequest {
    @NotNull
    private Long sourceId;      // именно sub-account
    private Long destId; // тоже sub-account (свой или чужой)
    @NotNull
    @DecimalMin("0.01")
    private BigDecimal amount;                 // сумма списания в валюте source
    private String description;
}

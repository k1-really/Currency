package com.kireally.Currency.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CurrencyAccountDto {
    Long id;
    String currencyType;
    BigDecimal balance;
}

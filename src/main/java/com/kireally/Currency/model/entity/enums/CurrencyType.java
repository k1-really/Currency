package com.kireally.Currency.model.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CurrencyType {
    USD("USD"),
    EUR("EUR"),
    GBP("GBP");

    private String name;

    public CurrencyType ofName(String name) {
        return CurrencyType.valueOf(name);
    }
}

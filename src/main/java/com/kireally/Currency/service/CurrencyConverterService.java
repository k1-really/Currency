package com.kireally.Currency.service;

import com.kireally.Currency.model.entity.enums.CurrencyType;
import com.kireally.Currency.service.impl.CurrencyConverterServiceImpl;

import java.math.BigDecimal;

public interface CurrencyConverterService {
    CurrencyConverterServiceImpl.CurrencyConvertResult convert(CurrencyType from, CurrencyType to, BigDecimal amount);
}

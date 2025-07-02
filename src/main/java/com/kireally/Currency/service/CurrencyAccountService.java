package com.kireally.Currency.service;

import com.kireally.Currency.model.dto.CurrencyAccountDto;
import com.kireally.Currency.model.entity.bankAccount.BankAccount;
import com.kireally.Currency.model.entity.bankAccount.CurrencyAccount;
import org.openapitools.model.CurrencyAccountCreate;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CurrencyAccountService {
    List<CurrencyAccountDto> getUserAccounts(Long accountId);
    Map<Long, CurrencyAccount> getAll(Set<Long> ids);
    List<CurrencyAccount> saveAll(Set<CurrencyAccount> currencyAccounts);
    List<CurrencyAccount> saveAll(List<CurrencyAccountCreate> currencyAccounts,
                                  BankAccount bankAccount);
}

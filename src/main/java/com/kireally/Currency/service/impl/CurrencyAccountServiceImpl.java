package com.kireally.Currency.service.impl;

import com.kireally.Currency.mapper.CurrencyAccountMapper;
import com.kireally.Currency.model.dto.CurrencyAccountDto;
import com.kireally.Currency.model.entity.bankAccount.BankAccount;
import com.kireally.Currency.model.entity.bankAccount.CurrencyAccount;
import com.kireally.Currency.repository.CurrencyAccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.CurrencyAccountCreate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyAccountServiceImpl {
    private final CurrencyAccountRepository currencyAccountRepository;
    private final CurrencyAccountMapper currencyAccountMapper;

    @Transactional
    public List<CurrencyAccountDto> getUserAccounts(Long accountId) {
        List<CurrencyAccount> accounts = currencyAccountRepository.findByBankAccount(accountId);
        return accounts.stream()
                .map(currencyAccountMapper::toDto)
                .toList();
    }

    @Transactional
    public Map<Long, CurrencyAccount> getAll(Set<Long> ids) {
        var cleanIds = ids.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        if (cleanIds.isEmpty()) {
            return Collections.emptyMap();
        }

        var accounts = currencyAccountRepository.findAllById(cleanIds);

        Set<Long> foundIds = accounts.stream()
                .map(CurrencyAccount::getId)
                .collect(Collectors.toSet());

        cleanIds.stream()
                .filter(id -> !foundIds.contains(id))
                .findFirst()
                .ifPresent(missing -> {
                    throw new EntityNotFoundException(
                            "CurrencyAccount not found: id=" + missing
                    );
                });

        return accounts.stream()
                .collect(Collectors.toMap(
                        CurrencyAccount::getId,
                        Function.identity()
                ));
    }

    @Transactional
    public List<CurrencyAccount> saveAll(Set<CurrencyAccount> currencyAccounts) {
        return currencyAccountRepository.saveAll(currencyAccounts);
    }

    @Transactional
    public List<CurrencyAccount> saveAll(List<CurrencyAccountCreate> currencyAccounts,
                                         BankAccount bankAccount) {
        var accounts = currencyAccounts.stream()
                .map(currencyAccountMapper::toEntity)
                .collect(Collectors.toList());

        for (CurrencyAccount account : accounts) {
            account.setBankAccount(bankAccount);
        }
        return currencyAccountRepository.saveAll(accounts);
    }
}

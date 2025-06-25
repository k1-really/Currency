package com.kireally.Currency.service.impl;

import com.kireally.Currency.exception.BankAccountValidationException;
import com.kireally.Currency.mapper.BankAccountMapper;
import com.kireally.Currency.model.entity.bankAccount.BankAccount;
import com.kireally.Currency.repository.BankAccountRepository;
import com.kireally.Currency.service.BankAccountService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.BankAccountCreateRequest;
import org.openapitools.model.BankAccountResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService{
    private final BankAccountRepository bankAccountRepository;
    private final BankAccountMapper bankAccountMapper;
    @Transactional
    public BankAccountResponse findById(@NotNull Long id) {
        var entity = bankAccountRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Bank account with id " + id + " not found")
        );
        return bankAccountMapper.toDto(entity);
    }

    @Transactional
    public Optional<BankAccount> findByAccount(String accountNumber) {
        return Optional.ofNullable(bankAccountRepository.findByNumber(accountNumber));
    }

    @Transactional
    public Optional<BankAccount> findOptionalById(@NotNull Long id) {
        try {
            return bankAccountRepository.findById(id);
        } catch (EntityNotFoundException e) {
            return Optional.empty();
        }
    }

    @Transactional
    public BankAccountResponse save(BankAccountCreateRequest request) {
        if (findByAccount(request.getNumber()).isPresent()) {
            throw new BankAccountValidationException(List.of("Account number " + request.getNumber() + " already exists."));
        }
        BankAccount entity = bankAccountRepository.save(
                bankAccountMapper.toEntity(request)
        );
        return bankAccountMapper.toDto(entity);
    }



}

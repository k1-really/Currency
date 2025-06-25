package com.kireally.Currency.service;

import com.kireally.Currency.model.entity.bankAccount.BankAccount;
import com.kireally.Currency.repository.BankAccountRepository;
import jakarta.persistence.EntityNotFoundException;
import org.openapitools.model.BankAccountCreateRequest;
import org.openapitools.model.BankAccountResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface BankAccountService {
    BankAccountResponse findById(Long id);
    Optional<BankAccount> findOptionalById(Long id);
    Optional<BankAccount> findByAccount(String accountNumber);
    BankAccountResponse save(BankAccountCreateRequest request);
}

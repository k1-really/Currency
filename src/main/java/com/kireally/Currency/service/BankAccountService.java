package com.kireally.Currency.service;

import com.kireally.Currency.model.entity.bankAccount.BankAccount;
import com.kireally.Currency.repository.BankAccountRepository;
import jakarta.persistence.EntityNotFoundException;
import org.openapitools.model.BankAccountCreateRequest;
import org.openapitools.model.BankAccountResponse;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface BankAccountService {
    BankAccountResponse findByCustomerId(@NotNull Long customerId);
    Optional<BankAccount> findById(@NotNull Long id);
    BankAccountResponse create(BankAccountCreateRequest request);
}

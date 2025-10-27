package com.kireally.Currency.service;

import com.kireally.Currency.model.entity.bankAccount.BankAccount;
import com.kireally.Currency.model.payment.BankAccountCreateRequest;
import com.kireally.Currency.model.payment.BankAccountResponse;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface BankAccountService {
    BankAccountResponse findByCustomerId(@NotNull Long customerId);
    Optional<BankAccount> findById(@NotNull Long id);
    BankAccountResponse create(BankAccountCreateRequest request);
}

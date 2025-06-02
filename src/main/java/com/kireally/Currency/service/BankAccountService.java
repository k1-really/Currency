package com.kireally.Currency.service;

import com.kireally.Currency.model.entity.bankAccount.BankAccount;
import com.kireally.Currency.repository.BankAccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface BankAccountService {
    Optional<BankAccount> findById(Long id);


}

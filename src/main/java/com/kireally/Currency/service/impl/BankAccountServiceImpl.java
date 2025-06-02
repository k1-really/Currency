package com.kireally.Currency.service.impl;

import com.kireally.Currency.model.entity.bankAccount.BankAccount;
import com.kireally.Currency.repository.BankAccountRepository;
import com.kireally.Currency.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService{
    private final BankAccountRepository bankAccountRepository;

    @Transactional
    public Optional<BankAccount> findById(Long id){
        return bankAccountRepository.findById(id);
    }
}

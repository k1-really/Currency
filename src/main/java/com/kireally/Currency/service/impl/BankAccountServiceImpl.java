package com.kireally.Currency.service.impl;

import com.kireally.Currency.mapper.BankAccountMapper;
import com.kireally.Currency.model.entity.bankAccount.BankAccount;
import com.kireally.Currency.model.entity.bankAccount.CurrencyAccount;
import com.kireally.Currency.repository.BankAccountRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.BankAccountCreateRequest;
import org.openapitools.model.BankAccountResponse;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Slf4j
@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl {
    private final BankAccountRepository bankAccountRepository;
    private final BankAccountMapper bankAccountMapper;
    private final CurrencyAccountServiceImpl currencyAccountService;

    @Transactional
    public BankAccountResponse findByCustomerId(@NotNull Long customerId) throws EntityNotFoundException {
        var entity = bankAccountRepository.findByCustomerId(customerId).orElseThrow(
                () -> new EntityNotFoundException("Bank account with customer id " + id + " not found")
        );
        return bankAccountMapper.toDto(entity);
    }

    @Transactional
    public Optional<BankAccount> findById(@NotNull Long id) {
        return bankAccountRepository.findById(id);
    }

    @Transactional
    public BankAccountResponse create(BankAccountCreateRequest request) {
        Optional<BankAccount> optionalAccount = bankAccountRepository.findByCustomerId(request.getCustomerId());

        if (optionalAccount.isPresent()) {
            log.info("Bank account with customer id {} already exists", optionalAccount.get().getCustomerId());
            return bankAccountMapper.toDto(optionalAccount.get());
        }
        BankAccount temp = bankAccountMapper.toEntity(request);
        BankAccount bankAccount = bankAccountRepository.save(temp);
        List<CurrencyAccount> currencyAccounts = currencyAccountService.saveAll(request.getCurrencyAccounts(), bankAccount);
        bankAccount.setCurrencyAccounts(currencyAccounts);

        return bankAccountMapper.toDto(bankAccount);
    }
}
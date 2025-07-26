package com.kireally.Currency.service.impl;

import com.kireally.Currency.exception.InsufficientFundsException;
import com.kireally.Currency.mapper.PaymentTransactionMapper;
import com.kireally.Currency.model.dto.paymentTransaction.CreatePaymentTransactionRequest;
import com.kireally.Currency.model.entity.bankAccount.CurrencyAccount;
import com.kireally.Currency.model.entity.bankAccount.PaymentTransaction;
import com.kireally.Currency.repository.PaymentTransactionRepository;
import com.kireally.Currency.service.PaymentTransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentTransactionServiceImpl implements PaymentTransactionService {
    private final PaymentTransactionRepository paymentTransactionRepository;
    private final PaymentTransactionMapper paymentTransactionMapper;
    private final CurrencyAccountServiceImpl currencyAccountService;
    private final CurrencyConverterServiceImpl currencyConverterService;
    private final TransactionTemplate transactionTemplate;

    @Transactional
    public PaymentTransaction save(PaymentTransaction paymentTransaction) {
        return paymentTransactionRepository.save(paymentTransaction);
    }

    @Transactional
    public Optional<PaymentTransaction> findById(@NotNull Long id) {
        return paymentTransactionRepository.findById(id);
    }

    /**
     * Переводит {@code amount} со счёта source на счет dest,
     * с конвертацией по курсу, если currency отличаются.
     *
     * @return сохранённая сущность транзакции.
     */
    public PaymentTransaction transfer(CreatePaymentTransactionRequest request) {
        var sourceId = request.getSourceId();
        var destId = request.getDestId();

        Map<Long, CurrencyAccount> accounts =
                currencyAccountService.getAll(Set.of(sourceId, destId));

        CurrencyAccount source = accounts.get(sourceId);
        CurrencyAccount dest = (destId != null ? accounts.get(destId) : null);

        // 2) Проверяем баланс
        if (source.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientFundsException(sourceId);
        }


        var isCurrencyExchangeNeeded = !source.getCurrencyType().equals(dest.getCurrencyType());
        PaymentTransaction paymentTransaction;
        BigDecimal destAmount;
        if (isCurrencyExchangeNeeded) {
            var currencyConvertResult = currencyConverterService.convert(
                    source.getCurrencyType(), dest.getCurrencyType(), request.getAmount());
            destAmount = currencyConvertResult.getAmount();
            paymentTransaction = paymentTransactionMapper.toEntity(
                    source,
                    dest,
                    request.getAmount(),
                    currencyConvertResult.getAmount(),
                    currencyConvertResult.getExchangeRate()
            );
        } else {
            destAmount = request.getAmount();
            paymentTransaction = paymentTransactionMapper.toEntity(
                    source,
                    dest,
                    request.getAmount(),
                    destAmount,
                    null
            );
        }

        PaymentTransaction finalPaymentTransaction = paymentTransaction;
        BigDecimal finalDestAmount = destAmount;

        return transactionTemplate.execute(status -> {
            source.setBalance(source.getBalance().subtract(request.getAmount()));
            dest.setBalance(dest.getBalance().add(finalDestAmount));
            currencyAccountService.saveAll(Set.of(source, dest));

            return save(finalPaymentTransaction);
        });
    }

}

package com.kireally.Currency.repository;

import com.kireally.Currency.model.entity.bankAccount.BankAccount;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    BankAccount findByNumber(String accountNumber);
    @EntityGraph(attributePaths = "currencyAccounts")
    Optional<BankAccount> findByCustomerId(Long customerId);
}

package com.kireally.Currency.repository;

import com.kireally.Currency.model.entity.bankAccount.CurrencyAccount;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyAccountRepository extends JpaRepository<CurrencyAccount, Long> {

    @Query(
            value = """
                      SELECT * 
                      FROM   currency_account
                      WHERE  bank_account_id = :accountId
                    """,
            nativeQuery = true
    )
    List<CurrencyAccount> findByBankAccount(@Param("accountId") Long accountId);
}

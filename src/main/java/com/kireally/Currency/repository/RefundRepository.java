package com.kireally.Currency.repository;

import com.kireally.Currency.model.entity.bankAccount.Refund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefundRepository extends JpaRepository<Refund, Long> {
    List<Refund> findAllByPaymentTransactionId(Long transactionId);
}


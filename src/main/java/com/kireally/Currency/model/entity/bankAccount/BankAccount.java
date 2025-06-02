package com.kireally.Currency.model.entity.bankAccount;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "bank_account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount extends BaseEntity{
    private String number;
    private BigDecimal balance;
    private Long customerId;
    private String currency;
}

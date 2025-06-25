package com.kireally.Currency.model.entity.bankAccount;

import com.kireally.Currency.model.entity.enums.CurrencyType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "currency_account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyAccount extends BaseEntity{

    private BigDecimal balance;

    @Enumerated(value = EnumType.STRING)
    private CurrencyType currency;
}

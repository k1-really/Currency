package com.kireally.Currency.model.entity.bankAccount;

import com.kireally.Currency.model.entity.enums.CurrencyType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "currency_account")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyAccount extends BaseEntity{

    @NotNull
    private CurrencyType currencyType;

    @PositiveOrZero
    private BigDecimal balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_account_id", nullable = false)
    private BankAccount bankAccount;

    @OneToMany(
            mappedBy = "source",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<PaymentTransaction> sourceTransactions = new ArrayList<>();

    @OneToMany(
            mappedBy = "destination",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<PaymentTransaction> transactions = new ArrayList<>();

}

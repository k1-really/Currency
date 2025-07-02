package com.kireally.Currency.model.entity.bankAccount;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "bank_account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount extends BaseEntity{
    @Column(name = "customer_id")
    @NotNull
    private Long customerId;

    @NotBlank
    private String number;

    @OneToMany(
            mappedBy = "bankAccount",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<CurrencyAccount> currencyAccounts;

}

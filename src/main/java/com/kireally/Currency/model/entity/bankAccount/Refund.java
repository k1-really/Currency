package com.kireally.Currency.model.entity.bankAccount;

import com.kireally.Currency.model.entity.enums.RefundStatus;
import com.kireally.Currency.model.entity.enums.converter.RefundStatusConverter;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "refund")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Refund extends BaseEntity{

    private BigDecimal refundedAmount;

    private String reason;

    @Convert(converter = RefundStatusConverter.class)
    private RefundStatus status;

    @ManyToOne
    @JoinColumn(name = "payment_transaction_id", referencedColumnName = "id")
    private PaymentTransaction paymentTransaction;

}

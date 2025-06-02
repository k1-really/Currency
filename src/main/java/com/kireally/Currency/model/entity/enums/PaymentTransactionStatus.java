package com.kireally.Currency.model.entity.enums;

import lombok.Getter;

@Getter
public enum PaymentTransactionStatus {
    PROCESSING, SUCCESS, FAILED;

    public static PaymentTransactionStatus fromString(String status){
        for(PaymentTransactionStatus paymentTransactionStatus : PaymentTransactionStatus.values()){
            if(paymentTransactionStatus.toString().equalsIgnoreCase(status)){
                return paymentTransactionStatus;
            }
        }
        throw new IllegalArgumentException("Invalid PaymentTransactionStatus: " + status);
    }
}

package com.kireally.Currency.exception.bankAccount;

public class BankAccountAlreadyExistsException extends RuntimeException{
    public BankAccountAlreadyExistsException(String message){
        super(message);
    };

}

package com.kireally.Currency.mapper;

import com.kireally.Currency.model.entity.bankAccount.BankAccount;
import com.kireally.Currency.model.payment.BankAccountCreateRequest;
import com.kireally.Currency.model.payment.BankAccountResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BankAccountMapper {
    BankAccountResponse toDto(BankAccount bankAccount);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "currencyAccounts", ignore = true)
    BankAccount toEntity(BankAccountCreateRequest bankAccountResponse);

}

package com.kireally.Currency.mapper;

import com.kireally.Currency.model.entity.bankAccount.BankAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.openapitools.model.BankAccountCreateRequest;
import org.openapitools.model.BankAccountResponse;

@Mapper(componentModel = "spring")
public interface BankAccountMapper {
    BankAccountResponse toDto(BankAccount bankAccount);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "currencyAccounts", ignore = true)
    BankAccount toEntity(BankAccountCreateRequest bankAccountResponse);

}

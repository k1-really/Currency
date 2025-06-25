package com.kireally.Currency.mapper;

import com.kireally.Currency.model.entity.bankAccount.BankAccount;
import org.mapstruct.Mapper;
import org.openapitools.model.BankAccountCreateRequest;
import org.openapitools.model.BankAccountResponse;

@Mapper(componentModel = "spring")
public interface BankAccountMapper {
    BankAccountResponse toDto(BankAccount bankAccount);
    BankAccount toEntity(BankAccountCreateRequest bankAccountResponse);

}

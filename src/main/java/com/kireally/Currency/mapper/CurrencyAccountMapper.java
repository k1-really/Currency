package com.kireally.Currency.mapper;


import com.kireally.Currency.model.dto.CurrencyAccountDto;
import com.kireally.Currency.model.entity.bankAccount.CurrencyAccount;
import com.kireally.Currency.model.entity.enums.CurrencyType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.openapitools.model.CurrencyAccountCreate;

@Mapper(componentModel = "spring")
public interface CurrencyAccountMapper {
    CurrencyAccountDto toDto(CurrencyAccount currencyAccount);

    @Mapping(source = "currency", target = "currencyType", qualifiedByName = "toCurrencyType")
    CurrencyAccount toEntity(CurrencyAccountCreate create);

    @Named("toCurrencyType")
    default CurrencyType toCurrencyType(String currency) {
        if (currency == null || currency.isBlank()) {
            return null;
        }
        try {
            return CurrencyType.valueOf(currency);
        } catch (IllegalArgumentException iae) {
            // you might want to log or rethrow a custom exception here
            return null;
        }
    }
}

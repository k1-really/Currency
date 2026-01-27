package com.kireally.Currency.controller.rest;

import com.kireally.Currency.model.dto.CurrencyAccountDto;
import com.kireally.Currency.service.impl.CurrencyAccountServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/currencyAccount")
@RequiredArgsConstructor
public class CurrencyAccountController {
    private final CurrencyAccountServiceImpl currencyAccountService;

    @Operation(summary = "Получить все валютные счета пользователя")
    @GetMapping
    public List<CurrencyAccountDto> getAccounts(
            @Parameter(description = "ID пользователя", required = true)
            @RequestParam Long userId
    ) {
        return currencyAccountService.getUserAccounts(userId);
    }

    @Operation(summary = "Получить валютный счет по Id")
    public CurrencyAccountDto getAccount(
            @Parameter(description = "ID счета", required = true)
            @RequestParam Long accountId
    ) {
        return currencyAccountService.getCurrencyAccount(accountId);
    }

}


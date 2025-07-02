package com.kireally.Currency.controller.rest;

import com.kireally.Currency.model.dto.CurrencyAccountDto;
import com.kireally.Currency.service.impl.CurrencyAccountServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/currencyAccounts")
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
}


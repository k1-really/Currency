package com.kireally.Currency.controller.rest;

import com.kireally.Currency.service.impl.BankAccountServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.api.AccountsApi;
import org.openapitools.model.BankAccountCreateRequest;
import org.openapitools.model.BankAccountResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BankAccountController implements AccountsApi {
    private final BankAccountServiceImpl bankAccountService;

    @Override
    public ResponseEntity<BankAccountResponse> getAccount(Long accountId) {
        BankAccountResponse response = bankAccountService.findByCustomerId(accountId);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<BankAccountResponse> createAccount(BankAccountCreateRequest bankAccountCreateRequest) {
        BankAccountResponse response = bankAccountService.create(bankAccountCreateRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}


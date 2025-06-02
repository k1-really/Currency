package com.kireally.Currency.controller.rest;

import com.kireally.Currency.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.openapitools.api.AccountsApi;
import org.openapitools.model.BankAccountCreateRequest;
import org.openapitools.model.BankAccountResponse;


@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BankAccountController implements AccountsApi {
    private final BankAccountService bankAccountService;

    @Override
    public ResponseEntity<BankAccountResponse> accountsAccountIdGet(Long accountId) {
        return ResponseEntity.ok(bankAccountService.findById(accountId));
    }

    @Override
    public ResponseEntity<BankAccountResponse> accountsPost(BankAccountCreateRequest bankAccountCreateRequest) {
        return ResponseEntity.ok(bankAccountService.save(bankAccountCreateRequest));
    }
}


package com.kireally.Currency.controllerAdvice.bankAccountControllerAdvice;

import com.kireally.Currency.controllerAdvice.errorResponse.CommonErrorResponse;
import com.kireally.Currency.exception.bankAccount.BankAccountAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class BankAccountControllerAdvice {
    @ExceptionHandler(BankAccountAlreadyExistsException.class)
    public ResponseEntity<CommonErrorResponse> handleBankAccountAlreadyExists(
            BankAccountAlreadyExistsException ex,
            HttpServletRequest request) {

        log.warn("Bank account already exists: {}", ex.getMessage());

        CommonErrorResponse error = CommonErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}

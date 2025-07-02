package com.kireally.Currency.validation.validators;

import com.kireally.Currency.exception.PaymentTransactionValidationException;
import com.kireally.Currency.model.dto.paymentTransaction.CancelPaymentRequest;
import com.kireally.Currency.model.dto.paymentTransaction.CreatePaymentTransactionRequest;
import com.kireally.Currency.model.entity.bankAccount.BankAccount;
import com.kireally.Currency.model.entity.bankAccount.PaymentTransaction;
import com.kireally.Currency.model.entity.bankAccount.Refund;
import com.kireally.Currency.model.entity.enums.PaymentTransactionStatus;
import com.kireally.Currency.service.BankAccountService;
import com.kireally.Currency.service.PaymentTransactionService;
import com.kireally.Currency.service.RefundService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.BankAccountResponse;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentTransactionValidator {
    private final Validator validator;

    public void validateCreateTransactionRequest(CreatePaymentTransactionRequest transaction) {

        Set<ConstraintViolation<CreatePaymentTransactionRequest>> violations = validator.validate(transaction, CreatePaymentTransactionRequest.class);
        List<String> violationMessages = violations.stream().map(ConstraintViolation::getMessage).toList();
        List<String> errors = new ArrayList<>(violationMessages);

        if(!errors.isEmpty()) {
            throw new PaymentTransactionValidationException(errors);
        }
    }

    public void validateCancelTransactionRequest(CancelPaymentRequest cancelPaymentRequest){
        List<String> errors = new ArrayList<>();

        Set<ConstraintViolation<CancelPaymentRequest>> violations = validator.validate(cancelPaymentRequest, CancelPaymentRequest.class);
        var violationMessages = violations.stream().map(ConstraintViolation::getMessage).toList();
        errors.addAll(violationMessages);

        // Если есть ошибки, выбрасываем исключение
        if (!errors.isEmpty()) {
            throw new PaymentTransactionValidationException(errors);
        }
    }

}

package com.kireally.Currency.validation.validators;

import com.kireally.Currency.exception.PaymentTransactionValidationException;
import com.kireally.Currency.model.dto.paymentTransaction.CancelPaymentRequest;
import com.kireally.Currency.model.dto.paymentTransaction.CreatePaymentTransactionRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
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

        if (!errors.isEmpty()) {
            throw new PaymentTransactionValidationException(errors);
        }
    }

}

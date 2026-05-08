package com.kireally.Currency.validation.validators;


import com.kireally.Currency.exception.PaymentTransactionValidationException;
import com.kireally.Currency.model.dto.paymentTransaction.CancelPaymentRequest;
import com.kireally.Currency.model.dto.paymentTransaction.CreatePaymentTransactionRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentTransactionValidatorTest {

    @Mock
    private Validator validator;

    private PaymentTransactionValidator paymentTransactionValidator;

    @BeforeEach
    void setUp() {
        paymentTransactionValidator = new PaymentTransactionValidator(validator);
    }

    @Test
    void shouldPassCreateRequestValidation_whenNoViolations() {
        CreatePaymentTransactionRequest request = new CreatePaymentTransactionRequest();

        when(validator.validate(eq(request), eq(CreatePaymentTransactionRequest.class)))
                .thenReturn(Collections.emptySet());

        assertDoesNotThrow(() ->
                paymentTransactionValidator.validateCreateTransactionRequest(request)
        );
    }

    @Test
    void shouldThrowException_whenCreateRequestHasViolations() {
        CreatePaymentTransactionRequest request = new CreatePaymentTransactionRequest();

        ConstraintViolation<CreatePaymentTransactionRequest> violation = mock(ConstraintViolation.class);
        when(violation.getMessage()).thenReturn("error1");

        Set<ConstraintViolation<CreatePaymentTransactionRequest>> violations = Set.of(violation);

        when(validator.validate(eq(request), eq(CreatePaymentTransactionRequest.class)))
                .thenReturn(violations);

        PaymentTransactionValidationException exception = assertThrows(
                PaymentTransactionValidationException.class,
                () -> paymentTransactionValidator.validateCreateTransactionRequest(request)
        );

        assertTrue(exception.getErrors().contains("error1"));
    }

    @Test
    void shouldPassCancelRequestValidation_whenNoViolations() {
        CancelPaymentRequest request = new CancelPaymentRequest();

        when(validator.validate(eq(request), eq(CancelPaymentRequest.class)))
                .thenReturn(Collections.emptySet());

        assertDoesNotThrow(() ->
                paymentTransactionValidator.validateCancelTransactionRequest(request)
        );
    }

    @Test
    void shouldThrowException_whenCancelRequestHasViolations() {
        CancelPaymentRequest request = new CancelPaymentRequest();

        ConstraintViolation<CancelPaymentRequest> violation = mock(ConstraintViolation.class);
        when(violation.getMessage()).thenReturn("cancel-error");

        Set<ConstraintViolation<CancelPaymentRequest>> violations = Set.of(violation);

        when(validator.validate(eq(request), eq(CancelPaymentRequest.class)))
                .thenReturn(violations);

        PaymentTransactionValidationException exception = assertThrows(
                PaymentTransactionValidationException.class,
                () -> paymentTransactionValidator.validateCancelTransactionRequest(request)
        );

        assertTrue(exception.getErrors().contains("cancel-error"));
    }
}
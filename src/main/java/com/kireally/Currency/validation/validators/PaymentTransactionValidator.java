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
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentTransactionValidator {
    private final BankAccountService bankAccountService;
    private final PaymentTransactionService paymentTransactionService;
    private final RefundService refundService;
    private final Validator validator;


    public void validateCreatePaymentTransactionRequest(CreatePaymentTransactionRequest transaction) {
        List<String> errors = new ArrayList<>();

        var violations = validator.validate(transaction, CreatePaymentTransactionRequest.class);
        var violationMessages = violations.stream().map(ConstraintViolation::getMessage).toList();
        errors.addAll(violationMessages);

        // Проверка наличия sourceAccountId
        if (bankAccountService.findOptionalById(transaction.getSourceBankAccountId()).isEmpty()) {
            errors.add("Source bank account does not exist: " + transaction.getSourceBankAccountId());
        }

        if (transaction.getDestinationBankAccountId() != null &&
                bankAccountService.findOptionalById(transaction.getDestinationBankAccountId()).isEmpty()) {
            errors.add("Destination bank account does not exist: " + transaction.getDestinationBankAccountId());
        }

        if(!errors.isEmpty()) {
            throw new PaymentTransactionValidationException(errors);
        }

    }

    public void validateCancelTransactionRequest(CancelPaymentRequest cancelPaymentRequest){
        List<String> errors = new ArrayList<>();

        var violations = validator.validate(cancelPaymentRequest, CancelPaymentRequest.class);
        var violationMessages = violations.stream().map(ConstraintViolation::getMessage).toList();
        errors.addAll(violationMessages);

        // Проверяем, существует ли транзакция
        PaymentTransaction sourceTransaction = paymentTransactionService.findOptionalById(cancelPaymentRequest.getTransactionId())
                .orElse(null);
        if (sourceTransaction == null) {
            errors.add("Transaction with ID " + cancelPaymentRequest.getTransactionId() + " does not exist.");
        } else {
            // Проверяем, завершена ли транзакция (FAILED или SUCCESS)
            if (!sourceTransaction.getStatus().equals(PaymentTransactionStatus.SUCCESS)) {
                errors.add("Transaction is not completed successfully, cancellation is not allowed.");
            }

            // Вычисляем уже возвращенную сумму.
            var alreadyRefunded = refundService.getTotalRefundedAmount(sourceTransaction.getId());
            var remainingAmount = cancelPaymentRequest.getRefundedAmount().subtract(alreadyRefunded);

            if (cancelPaymentRequest.getRefundedAmount().compareTo(remainingAmount) > 0) {
                errors.add("Cancel amount exceeds available refundable balance. Available: " + remainingAmount);
            }
        }

        // Если есть ошибки, выбрасываем исключение
        if (!errors.isEmpty()) {
            throw new PaymentTransactionValidationException(errors);
        }
    }

}

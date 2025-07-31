package com.kireally.Currency.controller.rest;

import com.kireally.Currency.controller.kafka.producer.PaymentTransactionProducer;
import com.kireally.Currency.model.dto.enums.CommandResultStatus;
import com.kireally.Currency.model.dto.paymentTransaction.CreatePaymentTransactionRequest;
import com.kireally.Currency.model.dto.paymentTransaction.CreatePaymentTransactionResponse;
import com.kireally.Currency.model.entity.enums.PaymentTransactionCommand;
import com.kireally.Currency.util.JsonConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
public class PaymentTransactionController {
    private static final String TOPIC = "payment-command";
    private final PaymentTransactionProducer producer;
    private final JsonConverter jsonConverter;

    @PostMapping("transfer")
    public ResponseEntity<CreatePaymentTransactionResponse> initiateTransfer(@RequestBody CreatePaymentTransactionRequest request){
        String requestId = generateRequestId().toString();
        try {
            // Отправка команды в Kafka
            producer.sendCommandResult(
                    TOPIC,
                    requestId,
                    PaymentTransactionCommand.CREATE,
                    jsonConverter.toJson(request)
            );

            return ResponseEntity.accepted()
                    .body(new CreatePaymentTransactionResponse(
                            CommandResultStatus.SUCCESS,
                            null,
                            LocalDateTime.now()
                    ));

        } catch (Exception ex) {
            log.error("Failed to process payment transfer, requestId: {}", requestId, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CreatePaymentTransactionResponse(
                            CommandResultStatus.FAILED,
                            "Failed to process payment request",
                            LocalDateTime.now()
                    ));
        }
    }

    private Long generateRequestId(){
        return UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }

}

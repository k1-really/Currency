package com.kireally.Currency.controller.kafka.test;

import com.kireally.Currency.controller.kafka.producer.PaymentTransactionProducer;
import com.kireally.Currency.model.dto.paymentTransaction.CancelPaymentRequest;
import com.kireally.Currency.model.dto.paymentTransaction.CreatePaymentTransactionRequest;
import com.kireally.Currency.model.entity.enums.PaymentTransactionCommand;
import com.kireally.Currency.util.JsonConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/test")
public class PaymentTransactionTest {
    private final JsonConverter jsonConverter;
    private final PaymentTransactionProducer producer;
    @PostMapping("/create-payment")
    public String createPaymentTest(){
        var requestId = "request-id";

        CreatePaymentTransactionRequest request = new CreatePaymentTransactionRequest(
                1L,
                2L,
                new BigDecimal("200.00"),
                "USD",
                "Test Payment");

        producer.sendCommandResult(Long.valueOf("1"),
                PaymentTransactionCommand.REFUND,
                jsonConverter.toJson(request)

        );
        return "Create payment command sent with requestId: " + requestId;
    }

    @PostMapping("/cancel-payment")
    public String cancelPaymentTest(){
        var requestId = "request-id";
        CancelPaymentRequest request = new CancelPaymentRequest(
                1L,
                new BigDecimal("150.00"),
                "Test Refund");
        producer.sendCommandResult(request.getTransactionId(),
                PaymentTransactionCommand.REFUND,
                jsonConverter.toJson(request)

        );
        return "Cancel payment command sent with requestId: " + requestId;
    }
}

package com.kireally.Currency.controller.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class LoadTestController {

    private final String instanceId;
    private final AtomicInteger activeRequests = new AtomicInteger(0);
    private final AtomicInteger totalRequests = new AtomicInteger(0);

    @Autowired(required = false)
    private KafkaTemplate<String, String> kafkaTemplate;

    public LoadTestController() throws UnknownHostException {
        this.instanceId = InetAddress.getLocalHost().getHostName();
    }

    // ===== БАЛАНСИРОВКА: ТЕСТОВЫЕ ЭНДПОИНТЫ =====

    @GetMapping("/fast")
    public Map<String, Object> fast() {
        return Map.of(
                "instance", instanceId,
                "active", activeRequests.get(),
                "total", totalRequests.incrementAndGet()
        );
    }

    @GetMapping("/slow")
    public Map<String, Object> slow() throws InterruptedException {
        activeRequests.incrementAndGet();
        totalRequests.incrementAndGet();

        Thread.sleep(2000);

        activeRequests.decrementAndGet();
        return Map.of(
                "instance", instanceId,
                "active", activeRequests.get(),
                "total", totalRequests.get(),
                "duration", "2 seconds"
        );
    }

    @GetMapping("/stats")
    public Map<String, Object> stats() {
        return Map.of(
                "instance", instanceId,
                "activeRequests", activeRequests.get(),
                "totalRequests", totalRequests.get()
        );
    }

    // ===== СИНХРОННОЕ ВЗАИМОДЕЙСТВИЕ (REST) =====

    @GetMapping("/rest/sync")
    public Map<String, Object> measureRestSync() {
        long startTime = System.currentTimeMillis();

        // Симулируем бизнес-логику (10ms)
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        long endTime = System.currentTimeMillis();
        long latencyMs = endTime - startTime;

        return Map.of(
                "type", "sync-rest",
                "instance", instanceId,
                "latencyMs", latencyMs,
                "timestamp", startTime
        );
    }

    // ===== АСИНХРОННОЕ ВЗАИМОДЕЙСТВИЕ (KAFKA) =====

    @PostMapping("/kafka/async")
    public Map<String, Object> measureKafkaAsync(@RequestBody(required = false) String message) {
        if (kafkaTemplate == null) {
            return Map.of("error", "Kafka не настроен", "instance", instanceId);
        }

        long startTime = System.currentTimeMillis();

        // Отправляем сообщение в Kafka
        String messageId = UUID.randomUUID().toString();
        String payload = message != null ? message : "test-message-" + messageId;
        kafkaTemplate.send("payment-command", messageId, payload);

        long endTime = System.currentTimeMillis();
        long latencyMs = endTime - startTime;

        return Map.of(
                "type", "async-kafka",
                "instance", instanceId,
                "messageId", messageId,
                "latencyMs", latencyMs,
                "timestamp", startTime
        );
    }
}
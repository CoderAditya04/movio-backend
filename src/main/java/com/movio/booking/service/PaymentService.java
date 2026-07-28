package com.movio.booking.service;

import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PaymentService {

    public PaymentResult processPayment(Double amount) {
        // simulate network/processing delay, like a real payment gateway round trip
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // simulate ~90% success rate
        boolean success = ThreadLocalRandom.current().nextInt(100) < 90;
        String transactionRef = "TXN-" + UUID.randomUUID().toString().substring(0, 12).toUpperCase();

        return new PaymentResult(success, transactionRef);
    }

    public record PaymentResult(boolean success, String transactionRef) {}
}
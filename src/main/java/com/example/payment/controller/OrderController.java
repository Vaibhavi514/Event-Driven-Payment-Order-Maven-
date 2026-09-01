package com.example.payment.controller;

import com.example.payment.model.OrderRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @PostMapping("/process")
    public ResponseEntity<Map<String, Object>> processPayment(@Valid @RequestBody OrderRequest request) {
        String transactionId = "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
            "orderId", request.orderId(),
            "transactionId", transactionId,
            "status", "PAYMENT_SUCCESSFUL",
            "amount", request.amount(),
            "currency", request.currency()
        ));
    }
}
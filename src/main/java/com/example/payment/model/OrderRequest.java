package com.example.payment.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record OrderRequest(
    @NotBlank(message = "Order ID is mandatory") String orderId,
    @NotBlank(message = "Customer ID is mandatory") String customerId,
    @NotNull(message = "Amount is mandatory") @DecimalMin(value = "0.01", message = "Amount must be greater than 0") BigDecimal amount,
    @NotBlank(message = "Currency is mandatory") String currency
) {}
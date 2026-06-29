package com.CMMS.Logistics.Dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class VehicleDeliveryRequestDto {
    private String invoice_number;
    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotNull(message = "Vehicle ID is required")
    private Long vehicleId;

    @NotNull(message = "Delivery date is required")
    @PastOrPresent(message = "Delivery date cannot be a future date")
    private LocalDate deliveryDate;

    @NotNull(message = "Invoice amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Invoice amount must be greater than 0")
    private BigDecimal invoiceAmount;

    @NotNull(message = "Delivering employee ID is required")
    private Long deliveredBy;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime lastModifiedAt;
    private Long lastModifiedBy;

}

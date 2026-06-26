package com.CMMS.Logistics.Dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class VehicleDeliveryResponseDto {
    private String invoice_number;
    private Long customerId;
    private Long vehicleId;
    private LocalDate deliveryDate;
    private BigDecimal invoiceAmount;
    private Long deliveredBy;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime lastModifiedAt;
    private Long lastModifiedBy;
}

package com.cmms.logistics.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Data
public class VehicleInventoryResponseDto {
    private Long id;
    private String vin;
    private Long productionOrder;
    private Long carModel;
    private String color;
    private String status;
    private LocalDate manufacturedDate;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime lastModifiedAt;
    private Long lastModifiedBy;
}
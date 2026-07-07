package com.cmms.logistics.dto;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
public class VehicleDeliveryRequestDto {

    private Long customerId;
    private Long vehicleId;
    private LocalDate deliveryDate;
    private BigDecimal invoiceAmount;
    private Long deliveredBy;

}

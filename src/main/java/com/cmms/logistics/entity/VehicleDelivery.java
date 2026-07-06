package com.cmms.logistics.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.SoftDelete;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "VehicleDelivery",schema = "CmmsTables")
@Data
@RequiredArgsConstructor
@SoftDelete(columnName = "is_delete")
public class VehicleDelivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String  invoiceNumber;


    @NotNull
    private Long customerId;

    @NotNull
    private Long vehicleId;

    @NotNull
    @PastOrPresent
    @Column(nullable = false)
    private LocalDate deliveryDate;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal invoiceAmount;

    @NotNull
    private Long deliveredBy;

    @NotNull
    @Column( nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @NotNull
    @Column( nullable = false, updatable = false)
    private Long createdBy;

    @NotNull
    @Column( nullable = false)
    private LocalDateTime lastModifiedAt;

    @NotNull
    @Column( nullable = false)
    private Long lastModifiedBy;
}

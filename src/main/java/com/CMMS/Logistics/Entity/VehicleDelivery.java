package com.CMMS.Logistics.Entity;

import com.CMMS.Human.Resources.Entity.Customer;
import com.CMMS.Master.Data.Entity.Employee;
import com.CMMS.Production.Entity.VehicleInventory;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "VehicleDelivery")
@Data
@RequiredArgsConstructor
public class VehicleDelivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String  invoice_number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @NotNull
    private Customer customerId;

    // One-to-One mapping used here to enforce that a vehicle can only be delivered once
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false, unique = true)
    @NotNull
    private VehicleInventory vehicleId;

    @NotNull
    @PastOrPresent
    @Column(name = "delivery_date", nullable = false)
    private LocalDate deliveryDate;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Column(name = "invoice_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal invoiceAmount;

    // Many Deliveries are processed by One Employee
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivered_by", nullable = false)
    @NotNull
    private Employee deliveredBy;

    // Audit Fields
    @NotNull
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @NotNull
    @Column(name = "created_by", nullable = false, updatable = false)
    private Long createdBy;

    @NotNull
    @Column(name = "last_modified_at", nullable = false)
    private LocalDateTime lastModifiedAt;

    @NotNull
    @Column(name = "last_modified_by", nullable = false)
    private Long lastModifiedBy;
}

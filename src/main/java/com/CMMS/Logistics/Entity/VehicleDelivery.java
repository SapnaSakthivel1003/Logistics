package com.CMMS.Logistics.Entity;

import com.CMMS.Human.Resources.Entity.Customer;
import com.CMMS.Master.Data.Entity.Employee;
import com.CMMS.Production.Entity.VehicleInventory;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table
@Data
public class VehicleDelivery {
    @Id
    private long id;
    private String invoice_number;
    @ManyToOne
    private VehicleInventory vehicle;
    @OneToOne
    private Customer customer;
    @Column(name = "delivery_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime delivery_date;
    private BigDecimal invoice_amount;
    private Employee delivered_by;
}

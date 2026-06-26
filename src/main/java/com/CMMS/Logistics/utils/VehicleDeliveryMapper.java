package com.CMMS.Logistics.utils;

import com.CMMS.Human.Resources.Entity.Customer;
import com.CMMS.Human.Resources.Repository.CustomerRepository;
import com.CMMS.Logistics.Dto.VehicleDeliveryRequestDto;
import com.CMMS.Logistics.Dto.VehicleDeliveryResponseDto;
import com.CMMS.Logistics.Entity.VehicleDelivery;
import com.CMMS.Master.Data.Entity.Employee;
import com.CMMS.Master.Data.Repository.EmployeeRepository;
import com.CMMS.Production.Entity.VehicleInventory;
import com.CMMS.Production.Repository.VehicleInventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class VehicleDeliveryMapper {
    private final VehicleInventoryRepository vehicleInventoryRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    public VehicleDelivery mapToEntity(VehicleDeliveryRequestDto dto) {
        VehicleInventory vehicleInventory = vehicleInventoryRepository.findById(dto.getVehicleId())
                .orElseThrow(() -> new RuntimeException("vehicleInventory not found"));
        Customer customer=customerRepository.findById(dto.getCustomerId()).orElseThrow(() -> new RuntimeException("customer not found"));
        Employee employee=employeeRepository.findById(dto.getDeliveredBy()).orElseThrow(() -> new RuntimeException("employee not found"));

        VehicleDelivery vehicleDelivery = new VehicleDelivery();
        vehicleDelivery.setInvoice_number(dto.getInvoice_number());
        vehicleDelivery.setVehicleId(vehicleInventory);
        vehicleDelivery.setCustomerId(customer);
        vehicleDelivery.setDeliveryDate(dto.getDeliveryDate());
        vehicleDelivery.setInvoiceAmount(dto.getInvoiceAmount());
        vehicleDelivery.setDeliveredBy(employee);
//        vehicleDelivery.setCreatedBy(dto.getCreatedBy());
        vehicleDelivery.setCreatedAt(LocalDateTime.now());
        vehicleDelivery.setLastModifiedAt(LocalDateTime.now());
//        vehicleDelivery.setLastModifiedBy(dto.getLastModifiedBy());
        return vehicleDelivery;
    }

    public VehicleDeliveryResponseDto mapToResponseDto(VehicleDelivery dto) {
        VehicleInventory vehicleInventory = vehicleInventoryRepository.findById(dto.getVehicleId().getId())
                .orElseThrow(() -> new RuntimeException("vehicleInventory not found"));
        Customer customer=customerRepository.findById(dto.getCustomerId().getId()).orElseThrow(() -> new RuntimeException("customer not found"));
        Employee employee=employeeRepository.findById(dto.getDeliveredBy().getId()).orElseThrow(() -> new RuntimeException("employee not found"));

        VehicleDeliveryResponseDto vehicleDelivery = new VehicleDeliveryResponseDto();
        vehicleDelivery.setInvoice_number(dto.getInvoice_number());
        vehicleDelivery.setVehicleId(vehicleInventory.getId());
        vehicleDelivery.setCustomerId(customer.getId());
        vehicleDelivery.setDeliveryDate(dto.getDeliveryDate());
        vehicleDelivery.setInvoiceAmount(dto.getInvoiceAmount());
        vehicleDelivery.setDeliveredBy(employee.getId());
//        vehicleDelivery.setCreatedBy(dto.getCreatedBy());
        vehicleDelivery.setCreatedAt(LocalDateTime.now());
        vehicleDelivery.setLastModifiedAt(LocalDateTime.now());
//        vehicleDelivery.setLastModifiedBy(dto.getLastModifiedBy());
        return vehicleDelivery;
    }
}

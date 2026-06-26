package com.CMMS.Logistics.Service;

import com.CMMS.Human.Resources.Entity.Customer;
import com.CMMS.Human.Resources.Repository.CustomerRepository;
import com.CMMS.Logistics.Dto.VehicleDeliveryRequestDto;
import com.CMMS.Logistics.Dto.VehicleDeliveryResponseDto;
import com.CMMS.Logistics.Entity.VehicleDelivery;
import com.CMMS.Logistics.Repository.VehicleDeliveryRepository;
import com.CMMS.Logistics.utils.VehicleDeliveryMapper;
import com.CMMS.Master.Data.Entity.Employee;
import com.CMMS.Master.Data.Repository.EmployeeRepository;
import com.CMMS.Production.Entity.VehicleInventory;
import com.CMMS.Production.Repository.VehicleInventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehicleDeliveryServiceImp implements VehicleDeliveryService{

    private final VehicleDeliveryRepository vehicleDeliveryRepository;
    private final VehicleInventoryRepository vehicleInventoryRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final VehicleDeliveryMapper mapper;
    @Override
    public VehicleDeliveryResponseDto saveVehicleDelivery(VehicleDeliveryRequestDto requestDto) {
        if (requestDto == null ||requestDto.getDeliveredBy() == null|| requestDto.getVehicleId() == null || requestDto.getCustomerId()==null) {
            throw new IllegalArgumentException("ID's must not be null in the request data.");
        }

        validateVehicleDeliveryExistence(requestDto.getVehicleId());
        validateCustomerExistence(requestDto.getCustomerId());
        validateEmployeeExistence(requestDto.getDeliveredBy());

        VehicleDelivery vehicleDelivery = mapper.mapToEntity(requestDto);
        String randomSuffix = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        String generatedCode = "Inc_No-" + randomSuffix;
        vehicleDelivery.setInvoice_number(generatedCode);

        vehicleDelivery.setCreatedAt(LocalDateTime.now());
        vehicleDelivery.setLastModifiedAt(LocalDateTime.now());
        VehicleDelivery savedVehicleDelivery = vehicleDeliveryRepository.save(vehicleDelivery);

        return mapper.mapToResponseDto(savedVehicleDelivery);
    }

    @Override
    public VehicleDeliveryResponseDto updateVehicleDelivery(Long id, VehicleDeliveryRequestDto requestDto) {
        VehicleDelivery existingVehicleDelivery = vehicleDeliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("VehicleInventory not found with ID: " + id));

        VehicleInventory vehicleInventory = vehicleInventoryRepository.findById(requestDto.getVehicleId())
                .orElseThrow(() -> new RuntimeException("vehicleInventory not found"));
        Customer customer=customerRepository.findById(requestDto.getCustomerId()).orElseThrow(() -> new RuntimeException("customer not found"));
        Employee employee=employeeRepository.findById(requestDto.getDeliveredBy()).orElseThrow(() -> new RuntimeException("employee not found"));

        existingVehicleDelivery.setInvoice_number(requestDto.getInvoice_number());
        existingVehicleDelivery.setVehicleId(vehicleInventory);
        existingVehicleDelivery.setCustomerId(customer);
        existingVehicleDelivery.setDeliveryDate(requestDto.getDeliveryDate());
        existingVehicleDelivery.setInvoiceAmount(requestDto.getInvoiceAmount());
        existingVehicleDelivery.setDeliveredBy(employee);
//        vehicleDelivery.setCreatedBy(dto.getCreatedBy());
        existingVehicleDelivery.setCreatedAt(LocalDateTime.now());
        existingVehicleDelivery.setLastModifiedAt(LocalDateTime.now());
//        vehicleDelivery.setLastModifiedBy(dto.getLastModifiedBy());

        VehicleDelivery vehicleDelivery = vehicleDeliveryRepository.save(existingVehicleDelivery);
        return mapper.mapToResponseDto(vehicleDelivery);
    }

    @Override

    public VehicleDeliveryResponseDto getById(Long id) {
        VehicleDelivery vehicleInventory = vehicleDeliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("product not found with ID: " + id));
        return mapper.mapToResponseDto(vehicleInventory);
    }

    @Override

    public List<VehicleDeliveryResponseDto> getAll() {
        return vehicleDeliveryRepository.findAll()
                .stream()
                .map(mapper::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        if (!vehicleDeliveryRepository.existsById(id)) {
            throw new RuntimeException("Cannot delete. Employee not found with ID: " + id);
        }
        vehicleDeliveryRepository.deleteById(id);
    }

    private void validateVehicleDeliveryExistence(Long vehicleId) {
        if (!vehicleDeliveryRepository.existsById(vehicleId)) {
            throw new IllegalArgumentException("Foreign key violation: Plant ID " + vehicleId + " does not exist.");
        }
    }
    private void validateCustomerExistence(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new IllegalArgumentException("Foreign key violation: Plant ID " + customerId + " does not exist.");
        }
    }
    private void validateEmployeeExistence(Long EmployeeId) {
        if (!employeeRepository.existsById(EmployeeId)) {
            throw new IllegalArgumentException("Foreign key violation: Plant ID " + EmployeeId + " does not exist.");
        }
    }
}

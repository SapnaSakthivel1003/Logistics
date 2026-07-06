package com.cmms.logistics.service;

import com.cmms.logistics.dto.VehicleDeliveryRequestDto;
import com.cmms.logistics.dto.VehicleDeliveryResponseDto;
import com.cmms.logistics.entity.VehicleDelivery;
import com.cmms.logistics.feignclients.CustomerClients;
import com.cmms.logistics.feignclients.EmployeeClients;
import com.cmms.logistics.feignclients.VehicleInventoryClients;
import com.cmms.logistics.repository.VehicleDeliveryRepository;
import com.cmms.logistics.user_context.UserContext;
import com.cmms.logistics.user_context.UserContextHolder;
import com.cmms.logistics.utils.VehicleDeliveryMapper;
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
   private final VehicleInventoryClients vehicleInventoryClients;
   private final EmployeeClients employeeClients;
   private final CustomerClients customerClients;

    private final VehicleDeliveryMapper mapper;
    @Override
    public VehicleDeliveryResponseDto saveVehicleDelivery(VehicleDeliveryRequestDto requestDto) {
        if (requestDto == null ||requestDto.getDeliveredBy() == null|| requestDto.getVehicleId() == null || requestDto.getCustomerId()==null) {
            throw new IllegalArgumentException("ID's must not be null in the request data.");
        }
        Boolean employeeExists = employeeClients.existsEmployeeById(requestDto.getDeliveredBy());
        Boolean customerExists = customerClients.existsCustomerById(requestDto.getCustomerId());
        Boolean vehicleExists = vehicleInventoryClients.existsVehicleInventoryById(requestDto.getVehicleId());
        if (Boolean.FALSE.equals(employeeExists)) {
            throw new IllegalArgumentException("Employee ID " + requestDto.getDeliveredBy() + " does not exist in master data.");
        }
        if (Boolean.FALSE.equals(customerExists)) {
            throw new IllegalArgumentException("Customer ID " + requestDto.getCustomerId() + " does not exist in master data.");
        }
        if (Boolean.FALSE.equals(vehicleExists)) {
            throw new IllegalArgumentException("vehicle ID " + requestDto.getVehicleId() + " does not exist in master data.");
        }

        UserContext context = UserContextHolder.getContext();
        Long userId = (context != null && context.getUserId() != null) ? context.getUserId() : 0L;
        VehicleDelivery vehicleDelivery = mapper.mapToEntity(requestDto);
        String randomSuffix = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        String generatedCode = "Inc_No-" + randomSuffix;
        vehicleDelivery.setInvoiceNumber(generatedCode);
        vehicleDelivery.setCreatedBy(userId);
        vehicleDelivery.setLastModifiedBy(userId);
        vehicleDelivery.setCreatedAt(LocalDateTime.now());
        vehicleDelivery.setLastModifiedAt(LocalDateTime.now());
        VehicleDelivery savedVehicleDelivery = vehicleDeliveryRepository.save(vehicleDelivery);

        return mapper.mapToResponseDto(savedVehicleDelivery);
    }

    @Override
    public VehicleDeliveryResponseDto updateVehicleDelivery(Long id, VehicleDeliveryRequestDto requestDto) {
        VehicleDelivery existingVehicleDelivery = vehicleDeliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("VehicleInventory not found with ID: " + id));

        Boolean employeeExists = employeeClients.existsEmployeeById(requestDto.getDeliveredBy());
        Boolean customerExists = customerClients.existsCustomerById(requestDto.getCustomerId());
        Boolean vehicleExists = vehicleInventoryClients.existsVehicleInventoryById(requestDto.getVehicleId());
        if (Boolean.FALSE.equals(employeeExists)) {
            throw new IllegalArgumentException("Employee ID " + requestDto.getDeliveredBy() + " does not exist in master data.");
        }
        if (Boolean.FALSE.equals(customerExists)) {
            throw new IllegalArgumentException("Customer ID " + requestDto.getCustomerId() + " does not exist in master data.");
        }
        if (Boolean.FALSE.equals(vehicleExists)) {
            throw new IllegalArgumentException("vehicle ID " + requestDto.getVehicleId() + " does not exist in master data.");
        }
        UserContext context = UserContextHolder.getContext();
        Long userId = (context != null && context.getUserId() != null) ? context.getUserId() : 0L;
        existingVehicleDelivery.setLastModifiedBy(userId);
        existingVehicleDelivery.setLastModifiedAt(LocalDateTime.now());


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



}

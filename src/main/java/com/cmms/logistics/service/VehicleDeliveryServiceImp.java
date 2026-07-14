package com.cmms.logistics.service;

import com.cmms.logistics.dto.*;
import com.cmms.logistics.entity.NotificationType;
import com.cmms.logistics.entity.RecipientRole;
import com.cmms.logistics.entity.VehicleDelivery;
import com.cmms.logistics.exception_handler.ApiResponse;
import com.cmms.logistics.feignclients.*;
import com.cmms.logistics.repository.VehicleDeliveryRepository;
import com.cmms.logistics.user_context.UserContext;
import com.cmms.logistics.user_context.UserContextHolder;
import com.cmms.logistics.utils.VehicleDeliveryMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class VehicleDeliveryServiceImp implements VehicleDeliveryService{

    private final VehicleDeliveryRepository vehicleDeliveryRepository;
   private final VehicleInventoryClients vehicleInventoryClients;
   private final NotificationsServiceImp notificationsServiceImp;
   private final EmployeeClients employeeClients;
   private final CustomerClients customerClients;
    private final AuditLogsFeignClient feignClient;
    private final ObjectMapper objectMapper;


    private final VehicleDeliveryMapper mapper;
    @Override
    @Transactional
    public VehicleDeliveryResponseDto saveVehicleDelivery(VehicleDeliveryRequestDto requestDto) {
        if (requestDto == null ||requestDto.getDeliveredBy() == null|| requestDto.getVehicleId() == null || requestDto.getCustomerId()==null) {
            throw new IllegalArgumentException("ID's must not be null in the request data.");
        }
        VehicleInventoryResponseDto vehicleInventoryResponseDto= vehicleInventoryClients.getQualityVehicleInventory(requestDto.getVehicleId()).getBody().getData();
        if (vehicleInventoryResponseDto == null) {
            throw new RuntimeException("VehicleInventory data is missing");
        }
        if (!"INSPECTED".equals(vehicleInventoryResponseDto.getStatus())) {
            throw new IllegalStateException("Vehicle must be in INSPECTED status to be delivered.");
        }
        EmployeeResponseDto employeeResponseDto= employeeClients.getEmployeeById(requestDto.getDeliveredBy()).getBody().getData();
        if (employeeResponseDto == null) {
            throw new RuntimeException("Employee data is missing");
        }
        ResponseEntity<ApiResponse<Boolean>> response = customerClients.getCustomerById(requestDto.getCustomerId());

        if (response.getBody() != null && response.getBody().getData() != null) {
            boolean customerExists = response.getBody().getData();

            if (!customerExists) {
                throw new RuntimeException("Customer does not exist or has been deleted.");
            }
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

        try {
            sendSyncRequest(savedVehicleDelivery, "CREATE");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            vehicleInventoryClients.updateVehicleStatus(requestDto.getVehicleId(), "DELIVERED");
            log.info("Successfully updated vehicle status internally.");
        } catch (feign.FeignException e) {
            log.error("Internal service update failed with status [{}]. Response: {}", e.status(), e.contentUTF8());
            throw new RuntimeException("Failed to update status internally: " + e.contentUTF8());
        }

        log.info(">>> [LOGISTICS] Saving notifications to Database...");
        try {
            sendNotifyRequest("ADMIN");
            sendNotifyRequest("PLANT_MANAGER");
            log.info(">>> [LOGISTICS] Both notifications executed smoothly.");
        } catch (Exception e) {
            log.error("!!! [NOTIFICATION CRASH DETECTED] Failed to save notifications: ", e);
            throw new RuntimeException("Notification table insertion failed: " + e.getMessage(), e);
        }
        return mapper.mapToResponseDto(savedVehicleDelivery);
    }

    @Override
    public VehicleDeliveryResponseDto updateVehicleDelivery(Long id, VehicleDeliveryRequestDto requestDto) {
        VehicleDelivery existingVehicleDelivery = vehicleDeliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("VehicleInventory not found with ID: " + id));

        VehicleInventoryResponseDto vehicleInventoryResponseDto= vehicleInventoryClients.getQualityVehicleInventory(requestDto.getVehicleId()).getBody().getData();
        if (vehicleInventoryResponseDto == null) {
            throw new RuntimeException("VehicleInventory data is missing");
        }
        EmployeeResponseDto employeeResponseDto= employeeClients.getEmployeeById(requestDto.getDeliveredBy()).getBody().getData();
        if (employeeResponseDto == null) {
            throw new RuntimeException("Employee data is missing");
        }
        ResponseEntity<ApiResponse<Boolean>> response = customerClients.getCustomerById(requestDto.getCustomerId());

        if (response.getBody() != null && response.getBody().getData() != null) {
            boolean customerExists = response.getBody().getData();

            if (!customerExists) {
                throw new RuntimeException("Customer does not exist or has been deleted.");
            }
        }
        UserContext context = UserContextHolder.getContext();
        Long userId = (context != null && context.getUserId() != null) ? context.getUserId() : 0L;
        existingVehicleDelivery.setLastModifiedBy(userId);
        existingVehicleDelivery.setLastModifiedAt(LocalDateTime.now());
        VehicleDelivery vehicleDelivery = vehicleDeliveryRepository.save(existingVehicleDelivery);
        try {
            sendNotifyRequest("ADMIN");
            sendNotifyRequest("PLANT_MANAGER");
            sendSyncRequest(vehicleDelivery, "UPDATE");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
        VehicleDelivery vehicleInventory = vehicleDeliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("product not found with ID: " + id));
        try {
            sendSyncRequest(vehicleInventory, "DELETE");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        vehicleDeliveryRepository.deleteById(id);
    }

    private void sendSyncRequest(VehicleDelivery vehicleDelivery, String actionStatus) throws Exception {
        String jsonPayload = objectMapper.writeValueAsString(vehicleDelivery);
        UserContext context = UserContextHolder.getContext();
        Long userId = (context != null && context.getUserId() != null) ? context.getUserId() : 0L;

        DataChangeEventDto dto = new DataChangeEventDto();
        dto.setAction(actionStatus);
        dto.setTableName("VehicleDelivery");
        dto.setChangedData(jsonPayload);
        dto.setPerformedBy(userId);
        dto.setRecordId(vehicleDelivery.getId());
        dto.setIpAddress(getSystemIpAddress());
        dto.setCreatedBy(userId);
        dto.setCreatedAt(LocalDateTime.now());
        dto.setLastModifiedBy(userId);
        dto.setLastModifiedAt(LocalDateTime.now());
        feignClient.createAuditLogs(dto);
    }


    public String getSystemIpAddress() {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            return localHost.getHostAddress();
        } catch (UnknownHostException e) {
            return "127.0.0.1";
        }
    }


    private void sendNotifyRequest(String role) {
        UserContext context = UserContextHolder.getContext();
        Long userId = (context != null && context.getUserId() != null) ? context.getUserId() : 0L;
        NotificationsRequestDto dto = new NotificationsRequestDto();
        dto.setRecipientRole(RecipientRole.valueOf(role));
        dto.setMessage("vehicle delivered");
        dto.setRead(false);
        dto.setNotificationType(NotificationType.valueOf("DELIVERY"));
        dto.setCreatedBy(userId);
        dto.setCreatedAt(LocalDateTime.now());
        dto.setLastModifiedBy(userId);
        dto.setLastModifiedAt(LocalDateTime.now());
        notificationsServiceImp.saveNotifications(dto);

    }

}

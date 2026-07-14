package com.cmms.logistics.controller;

import com.cmms.logistics.dto.VehicleDeliveryRequestDto;
import com.cmms.logistics.dto.VehicleDeliveryResponseDto;
import com.cmms.logistics.exception_handler.ApiResponse;
import com.cmms.logistics.service.VehicleDeliveryService;
import com.cmms.logistics.user_context.RequireRole;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/logistics/vehicleDelivery")
@RequiredArgsConstructor
public class VehicleDeliveryController {

    private final VehicleDeliveryService vehicleDeliveryService;
    @PostMapping
    @RequireRole("ROLE_ADMIN")
    public ResponseEntity<ApiResponse<VehicleDeliveryResponseDto>> createVehicleDelivery(@RequestBody(required = true) VehicleDeliveryRequestDto requestDto) {
        VehicleDeliveryResponseDto savedVehicleInventory = vehicleDeliveryService.saveVehicleDelivery(requestDto);
        ApiResponse<VehicleDeliveryResponseDto> response = ApiResponse.success(
                HttpStatus.CREATED.value(),
                "VehicleDelivery created successfully.",
                savedVehicleInventory
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @RequireRole("ROLE_ADMIN")
    public ResponseEntity<ApiResponse<VehicleDeliveryResponseDto>> getVehicleDeliveryById(@PathVariable Long id) {
        VehicleDeliveryResponseDto vehicleDelivery = vehicleDeliveryService.getById(id);
        ApiResponse<VehicleDeliveryResponseDto> response = ApiResponse.success(
                HttpStatus.OK.value(),
                "VehicleDelivery retrieved successfully.",
                vehicleDelivery
        );
        return ResponseEntity.ok(response);
    }


    @GetMapping
    @RequireRole("ROLE_ADMIN")
    public ResponseEntity<ApiResponse<List<VehicleDeliveryResponseDto>>> getAllVehicleDelivery() {
        List<VehicleDeliveryResponseDto> vehicleDelivery = vehicleDeliveryService.getAll();
        ApiResponse<List<VehicleDeliveryResponseDto>> response = ApiResponse.success(
                HttpStatus.OK.value(),
                "All VehicleDelivery retrieved successfully.",
                vehicleDelivery
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @RequireRole("ROLE_ADMIN")
    public ResponseEntity<ApiResponse<VehicleDeliveryResponseDto>> updateVehicleDelivery(
            @PathVariable Long id,
            @Valid @RequestBody VehicleDeliveryRequestDto requestDto) {
        VehicleDeliveryResponseDto updatedVehicleInventory = vehicleDeliveryService.updateVehicleDelivery(id, requestDto);
        ApiResponse<VehicleDeliveryResponseDto> response = ApiResponse.success(
                HttpStatus.OK.value(),
                "VehicleDelivery updated successfully.",
                updatedVehicleInventory
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @RequireRole("ROLE_ADMIN")
    public ResponseEntity<ApiResponse<Void>> deleteVehicleDelivery(@PathVariable Long id) {
        vehicleDeliveryService.deleteById(id);
        ApiResponse<Void> response = ApiResponse.success(
                HttpStatus.OK.value(),
                "VehicleDelivery deleted successfully.",
                null
        );
        return ResponseEntity.ok(response);
    }
}

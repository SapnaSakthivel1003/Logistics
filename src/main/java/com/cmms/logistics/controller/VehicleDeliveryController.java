package com.cmms.logistics.controller;

import com.cmms.logistics.dto.VehicleDeliveryRequestDto;
import com.cmms.logistics.dto.VehicleDeliveryResponseDto;
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
    public ResponseEntity<VehicleDeliveryResponseDto> createVehicleDelivery(@RequestBody(required = true) VehicleDeliveryRequestDto requestDto) {
        VehicleDeliveryResponseDto savedVehicleInventory = vehicleDeliveryService.saveVehicleDelivery(requestDto);
        return new ResponseEntity<>(savedVehicleInventory, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @RequireRole("ROLE_ADMIN")
    public ResponseEntity<VehicleDeliveryResponseDto> getVehicleDeliveryById(@PathVariable Long id) {
        VehicleDeliveryResponseDto vehicleDelivery = vehicleDeliveryService.getById(id);
        return ResponseEntity.ok(vehicleDelivery);
    }


    @GetMapping
    @RequireRole("ROLE_ADMIN")
    public ResponseEntity<List<VehicleDeliveryResponseDto>> getAllVehicleDelivery() {
        return ResponseEntity.ok(vehicleDeliveryService.getAll());
    }

    @PutMapping("/{id}")
    @RequireRole("ROLE_ADMIN")
    public ResponseEntity<VehicleDeliveryResponseDto> updateVehicleDelivery(
            @PathVariable Long id,
            @Valid @RequestBody VehicleDeliveryRequestDto requestDto) {
        VehicleDeliveryResponseDto updatedVehicleInventory = vehicleDeliveryService.updateVehicleDelivery(id, requestDto);
        return ResponseEntity.ok(updatedVehicleInventory);
    }

    @DeleteMapping("/{id}")
    @RequireRole("ROLE_ADMIN")
    public ResponseEntity<Void> deleteVehicleDelivery(@PathVariable Long id) {
        vehicleDeliveryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

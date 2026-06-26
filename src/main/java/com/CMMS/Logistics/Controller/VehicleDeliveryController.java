package com.CMMS.Logistics.Controller;

import com.CMMS.Logistics.Dto.VehicleDeliveryRequestDto;
import com.CMMS.Logistics.Dto.VehicleDeliveryResponseDto;
import com.CMMS.Logistics.Service.VehicleDeliveryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/production/vehicleDelivery")
@RequiredArgsConstructor
public class VehicleDeliveryController {

    private final VehicleDeliveryService vehicleDeliveryService;
    @PostMapping
    public ResponseEntity<VehicleDeliveryResponseDto> createVehicleDelivery(@RequestBody(required = true) VehicleDeliveryRequestDto requestDto) {
        System.err.println(requestDto);
        VehicleDeliveryResponseDto savedVehicleInventory = vehicleDeliveryService.saveVehicleDelivery(requestDto);
        return new ResponseEntity<>(savedVehicleInventory, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleDeliveryResponseDto> getVehicleDeliveryById(@PathVariable Long id) {
        VehicleDeliveryResponseDto vehicleDelivery = vehicleDeliveryService.getById(id);
        return ResponseEntity.ok(vehicleDelivery);
    }


    @GetMapping
    public ResponseEntity<List<VehicleDeliveryResponseDto>> getAllVehicleDelivery() {
        return ResponseEntity.ok(vehicleDeliveryService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleDeliveryResponseDto> updateVehicleDelivery(
            @PathVariable Long id,
            @Valid @RequestBody VehicleDeliveryRequestDto requestDto) {
        VehicleDeliveryResponseDto updatedVehicleInventory = vehicleDeliveryService.updateVehicleDelivery(id, requestDto);
        return ResponseEntity.ok(updatedVehicleInventory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicleDelivery(@PathVariable Long id) {
        vehicleDeliveryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

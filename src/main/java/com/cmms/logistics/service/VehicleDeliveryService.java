package com.cmms.logistics.service;

import com.cmms.logistics.dto.VehicleDeliveryRequestDto;
import com.cmms.logistics.dto.VehicleDeliveryResponseDto;

import java.util.List;

public interface VehicleDeliveryService {
    VehicleDeliveryResponseDto saveVehicleDelivery(VehicleDeliveryRequestDto requestDto);
    VehicleDeliveryResponseDto getById(Long id);
    List<VehicleDeliveryResponseDto> getAll();
    VehicleDeliveryResponseDto updateVehicleDelivery(Long id, VehicleDeliveryRequestDto requestDto);
    void deleteById(Long id);
}

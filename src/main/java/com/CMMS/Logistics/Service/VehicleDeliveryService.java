package com.CMMS.Logistics.Service;

import com.CMMS.Logistics.Dto.VehicleDeliveryRequestDto;
import com.CMMS.Logistics.Dto.VehicleDeliveryResponseDto;
import com.CMMS.Production.Dto.VehicleInventoryRequestDto;
import com.CMMS.Production.Dto.VehicleInventoryResponseDto;

import java.util.List;

public interface VehicleDeliveryService {
    VehicleDeliveryResponseDto saveVehicleDelivery(VehicleDeliveryRequestDto requestDto);
    VehicleDeliveryResponseDto getById(Long id);
    List<VehicleDeliveryResponseDto> getAll();
    VehicleDeliveryResponseDto updateVehicleDelivery(Long id, VehicleDeliveryRequestDto requestDto);
    void deleteById(Long id);
}

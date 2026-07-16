package com.cmms.logistics.utils;


import com.cmms.logistics.dto.VehicleDeliveryRequestDto;
import com.cmms.logistics.dto.VehicleDeliveryResponseDto;
import com.cmms.logistics.entity.VehicleDelivery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
public class VehicleDeliveryMapper {


    public VehicleDelivery mapToEntity(VehicleDeliveryRequestDto dto) {
        VehicleDelivery vehicleDelivery = new VehicleDelivery();
        vehicleDelivery.setVehicleId(dto.getVehicleId());
        vehicleDelivery.setCustomerId(dto.getCustomerId());
        vehicleDelivery.setDeliveryDate(dto.getDeliveryDate());
        vehicleDelivery.setInvoiceAmount(dto.getInvoiceAmount());
        vehicleDelivery.setDeliveredBy(dto.getDeliveredBy());

        return vehicleDelivery;
    }
    public VehicleDeliveryResponseDto mapToResponseDto(VehicleDelivery dto) {

        VehicleDeliveryResponseDto vehicleDelivery = new VehicleDeliveryResponseDto();
        vehicleDelivery.setId(dto.getId());
        vehicleDelivery.setInvoiceNumber(dto.getInvoiceNumber());
        vehicleDelivery.setVehicleId(dto.getVehicleId());
        vehicleDelivery.setCustomerId(dto.getCustomerId());
        vehicleDelivery.setDeliveryDate(dto.getDeliveryDate());
        vehicleDelivery.setInvoiceAmount(dto.getInvoiceAmount());
        vehicleDelivery.setDeliveredBy(dto.getDeliveredBy());
        vehicleDelivery.setCreatedAt(dto.getCreatedAt());
        vehicleDelivery.setLastModifiedAt(dto.getLastModifiedAt());
        vehicleDelivery.setCreatedBy(dto.getCreatedBy());
        vehicleDelivery.setLastModifiedBy(dto.getLastModifiedBy());
        return vehicleDelivery;
    }
}

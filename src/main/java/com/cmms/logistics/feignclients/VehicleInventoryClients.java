package com.cmms.logistics.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "vehicle-service", url = "http://localhost:8083/api/v1",configuration = FeignClientSecurityConfig.class)
public interface VehicleInventoryClients {

    @GetMapping("/production/vehicleInventory/{id}")
    ResponseEntity<VehicleInventoryApiResponse> getQualityVehicleInventory(@PathVariable("id") long id);

    @PutMapping("/production/vehicleInventory/{id}/status")
    void updateVehicleStatus(
            @PathVariable("id") long id,
            @RequestParam("status") String status
    );

}

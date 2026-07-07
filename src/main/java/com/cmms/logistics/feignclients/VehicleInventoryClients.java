package com.cmms.logistics.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "vehicle-service", url = "http://localhost:8083/api/v1",configuration = FeignClientSecurityConfig.class)
public interface VehicleInventoryClients {
    @GetMapping("/production/vehicleInventory/exists/{id}")
    Boolean existsVehicleInventoryById(@PathVariable("id") Long id);
}

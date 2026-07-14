package com.cmms.logistics.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "master-service", url = "http://localhost:8081/api/v1",configuration =FeignClientSecurityConfig.class)
public interface EmployeeClients {
    @GetMapping("/master-data/employee/{id}")
    ResponseEntity<EmployeeApiResponse> getEmployeeById(@PathVariable("id") long id);
}

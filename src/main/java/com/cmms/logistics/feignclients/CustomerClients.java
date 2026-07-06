package com.cmms.logistics.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "hr-service", url = "http://localhost:8082/api/v1",configuration =FeignClientSecurityConfig.class)
public interface CustomerClients {
    @GetMapping("/hr/customer/exists/{id}")
    Boolean existsCustomerById(@PathVariable("id") Long id);
}

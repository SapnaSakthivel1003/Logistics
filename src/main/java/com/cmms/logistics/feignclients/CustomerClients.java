package com.cmms.logistics.feignclients;


import com.cmms.logistics.exception_handler.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "hr-service", url = "http://localhost:8082/api/v1",configuration =FeignClientSecurityConfig.class)
public interface CustomerClients {
    @GetMapping("/hr/customer/exists/{id}")
    ResponseEntity<ApiResponse<Boolean>> getCustomerById(@PathVariable("id") long id);

}

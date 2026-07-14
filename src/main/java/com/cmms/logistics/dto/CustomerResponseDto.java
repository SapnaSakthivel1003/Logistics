package com.cmms.logistics.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CustomerResponseDto {
    private Long id;
    private String customerName;
    private String contactNumber;
    private String email;
    private String address;
    private Boolean isActive = true;
    private Long createdBy;
    private Long lastModifiedBy;
    private LocalDateTime  createdAt;
    private LocalDateTime lastModifiedAt;
}

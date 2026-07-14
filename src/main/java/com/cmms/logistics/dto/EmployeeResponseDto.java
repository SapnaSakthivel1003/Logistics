package com.cmms.logistics.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class EmployeeResponseDto {
 private Long id;
    private String employeeCode;
    private String fullName;
    private Long plantId;
    private String designation;
    private LocalDate dateOfBirth;
    private LocalDate joiningDate;
    private byte[] profileImage;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime lastModifiedAt;
    private Long lastModifiedBy;
}

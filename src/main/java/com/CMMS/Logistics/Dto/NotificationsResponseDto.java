package com.CMMS.Logistics.Dto;

import com.CMMS.Logistics.Entity.NotificationType;
import com.CMMS.Logistics.Entity.RecipientRole;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationsResponseDto {
    private RecipientRole recipientRole;
    private String message;
    private NotificationType notificationType;
    private boolean isRead;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime lastModifiedAt;
    private Long lastModifiedBy;
}

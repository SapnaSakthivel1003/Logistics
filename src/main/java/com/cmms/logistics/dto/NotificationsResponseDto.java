package com.cmms.logistics.dto;

import com.cmms.logistics.entity.NotificationType;
import com.cmms.logistics.entity.RecipientRole;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationsResponseDto {
    private Long id;
    private RecipientRole recipientRole;
    private String message;
    private NotificationType notificationType;
    private boolean isRead;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime lastModifiedAt;
    private Long lastModifiedBy;
}

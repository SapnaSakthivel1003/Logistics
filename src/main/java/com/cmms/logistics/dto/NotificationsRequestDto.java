package com.cmms.logistics.dto;

import com.cmms.logistics.entity.NotificationType;
import com.cmms.logistics.entity.RecipientRole;
import lombok.Data;


@Data
public class NotificationsRequestDto {
    private RecipientRole recipientRole;
    private String message;
    private NotificationType notificationType;
    private boolean isRead;
}

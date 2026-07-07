package com.cmms.logistics.utils;

import com.cmms.logistics.dto.NotificationsRequestDto;
import com.cmms.logistics.dto.NotificationsResponseDto;
import com.cmms.logistics.entity.Notifications;
import org.springframework.stereotype.Component;


@Component
public class NotificationsMapper {

    public Notifications mapToEntity(NotificationsRequestDto dto) {

        Notifications notifications = new Notifications();
        notifications.setRecipientRole(dto.getRecipientRole());
        notifications.setMessage(dto.getMessage());
        notifications.setNotificationType(dto.getNotificationType());
        notifications.setRead(dto.isRead());
        return notifications;
    }

    public NotificationsResponseDto mapToResponseDto(Notifications dto) {

        NotificationsResponseDto notifications = new NotificationsResponseDto();
        notifications.setId(dto.getId());
        notifications.setRecipientRole(dto.getRecipientRole());
        notifications.setMessage(dto.getMessage());
        notifications.setNotificationType(dto.getNotificationType());
        notifications.setRead(dto.isRead());
        notifications.setCreatedBy(dto.getCreatedBy());
        notifications.setCreatedAt(dto.getCreatedAt());
        notifications.setLastModifiedAt(dto.getLastModifiedAt());
        notifications.setLastModifiedBy(dto.getLastModifiedBy());
        return notifications;
    }
}

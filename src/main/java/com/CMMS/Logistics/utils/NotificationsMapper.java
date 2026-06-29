package com.CMMS.Logistics.utils;

import com.CMMS.Logistics.Dto.NotificationsRequestDto;
import com.CMMS.Logistics.Dto.NotificationsResponseDto;
import com.CMMS.Logistics.Entity.Notifications;
import java.time.LocalDateTime;

public class NotificationsMapper {

    public Notifications mapToEntity(NotificationsRequestDto dto) {

        Notifications notifications = new Notifications();
        notifications.setRecipientRole(dto.getRecipientRole());
        notifications.setMessage(dto.getMessage());
        notifications.setNotificationType(dto.getNotificationType());
        notifications.setRead(dto.isRead());

//        vehicleDelivery.setCreatedBy(dto.getCreatedBy());
        notifications.setCreatedAt(LocalDateTime.now());
        notifications.setLastModifiedAt(LocalDateTime.now());
//        vehicleDelivery.setLastModifiedBy(dto.getLastModifiedBy());
        return notifications;
    }

    public NotificationsResponseDto mapToResponseDto(Notifications dto) {

        NotificationsResponseDto notifications = new NotificationsResponseDto();
        notifications.setRecipientRole(dto.getRecipientRole());
        notifications.setMessage(dto.getMessage());
        notifications.setNotificationType(dto.getNotificationType());
        notifications.setRead(dto.isRead());

//        vehicleDelivery.setCreatedBy(dto.getCreatedBy());
        notifications.setCreatedAt(LocalDateTime.now());
        notifications.setLastModifiedAt(LocalDateTime.now());
//        vehicleDelivery.setLastModifiedBy(dto.getLastModifiedBy());
        return notifications;
    }
}

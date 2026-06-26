package com.CMMS.Logistics.Service;

import com.CMMS.Logistics.Dto.NotificationsRequestDto;
import com.CMMS.Logistics.Dto.NotificationsResponseDto;
import com.CMMS.Logistics.Entity.Notifications;
import com.CMMS.Logistics.Repository.NotificationRepository;
import com.CMMS.Logistics.utils.NotificationsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationsServiceImp implements NotificationsService{
    private final NotificationRepository notificationRepository;
    private final NotificationsMapper notificationsMapper;

    @Override
    public NotificationsResponseDto saveNotifications(NotificationsRequestDto requestDto) {

        Notifications notifications = notificationsMapper.mapToEntity(requestDto);
        notifications.setCreatedAt(LocalDateTime.now());
        notifications.setLastModifiedAt(LocalDateTime.now());
        Notifications savedNotifications= notificationRepository.save(notifications);
        return notificationsMapper.mapToResponseDto(savedNotifications);
    }

    @Override
    public NotificationsResponseDto updateNotifications(Long id, NotificationsRequestDto requestDto) {
        Notifications existingNotifications = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("notifications not found with ID: " + id));
        existingNotifications.setRecipientRole(requestDto.getRecipientRole());
        existingNotifications.setMessage(requestDto.getMessage());
        existingNotifications.setNotificationType(requestDto.getNotificationType());
        existingNotifications.setRead(requestDto.isRead());
        existingNotifications.setLastModifiedAt(LocalDateTime.now());
        existingNotifications.setCreatedAt(LocalDateTime.now());
        Notifications updatedNotifications = notificationRepository.save(existingNotifications);

        return notificationsMapper.mapToResponseDto(updatedNotifications);
    }

    @Override

    public NotificationsResponseDto getById(Long id) {
        Notifications notifications = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));
        return notificationsMapper.mapToResponseDto(notifications);
    }

    @Override

    public List<NotificationsResponseDto> getAll() {
        return notificationRepository.findAll()
                .stream()
                .map(notificationsMapper::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        if (!notificationRepository.existsById(id)) {
            throw new RuntimeException("Cannot delete. Employee not found with ID: " + id);
        }
        notificationRepository.deleteById(id);
    }
}

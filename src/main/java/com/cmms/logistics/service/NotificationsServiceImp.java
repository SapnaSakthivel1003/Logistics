package com.cmms.logistics.service;

import com.cmms.logistics.dto.NotificationsRequestDto;
import com.cmms.logistics.dto.NotificationsResponseDto;
import com.cmms.logistics.entity.Notifications;
import com.cmms.logistics.repository.NotificationRepository;
import com.cmms.logistics.user_context.UserContext;
import com.cmms.logistics.user_context.UserContextHolder;
import com.cmms.logistics.utils.NotificationsMapper;
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
        UserContext context = UserContextHolder.getContext();
        Long userId = (context != null && context.getUserId() != null) ? context.getUserId() : 0L;
        Notifications notifications = notificationsMapper.mapToEntity(requestDto);
        notifications.setCreatedBy(userId);
        notifications.setCreatedAt(LocalDateTime.now());
        notifications.setLastModifiedAt(LocalDateTime.now());
        notifications.setLastModifiedBy(userId);
        Notifications savedNotifications= notificationRepository.save(notifications);
        return notificationsMapper.mapToResponseDto(savedNotifications);
    }

    @Override
    public NotificationsResponseDto updateNotifications(Long id, NotificationsRequestDto requestDto) {
        Notifications existingNotifications = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("notifications not found with ID: " + id));
        UserContext context = UserContextHolder.getContext();
        Long userId = (context != null && context.getUserId() != null) ? context.getUserId() : 0L;

        existingNotifications.setLastModifiedAt(LocalDateTime.now());
        existingNotifications.setLastModifiedBy(userId);

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

package com.cmms.logistics.service;

import com.cmms.logistics.dto.NotificationsRequestDto;
import com.cmms.logistics.dto.NotificationsResponseDto;
import com.cmms.logistics.entity.Notifications;
import com.cmms.logistics.feignclients.AuditLogsFeignClient;
import com.cmms.logistics.feignclients.DataChangeEventDto;
import com.cmms.logistics.repository.NotificationRepository;
import com.cmms.logistics.user_context.UserContext;
import com.cmms.logistics.user_context.UserContextHolder;
import com.cmms.logistics.utils.NotificationsMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationsServiceImp implements NotificationsService{
    private final NotificationRepository notificationRepository;
    private final NotificationsMapper notificationsMapper;
    private final AuditLogsFeignClient feignClient;
    private final ObjectMapper objectMapper;

    @Transactional
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
        try {
            sendSyncRequest(savedNotifications, "CREATE");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
        try {
            sendSyncRequest(updatedNotifications, "UPDATE");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

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
        Notifications notifications = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));
        try {
            sendSyncRequest(notifications, "CREATE");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        notificationRepository.deleteById(id);
    }
    private void sendSyncRequest(Notifications notifications, String actionStatus) throws Exception {
        String jsonPayload = objectMapper.writeValueAsString(notifications);
        UserContext context = UserContextHolder.getContext();
        Long userId = (context != null && context.getUserId() != null) ? context.getUserId() : 0L;

        DataChangeEventDto dto = new DataChangeEventDto();
        dto.setAction(actionStatus);
        dto.setTableName("Notifications");
        dto.setChangedData(jsonPayload);
        dto.setPerformedBy(userId);
        dto.setRecordId(notifications.getId());
        dto.setIpAddress(getSystemIpAddress());
        dto.setCreatedBy(userId);
        dto.setCreatedAt(LocalDateTime.now());
        dto.setLastModifiedBy(userId);
        dto.setLastModifiedAt(LocalDateTime.now());
        feignClient.createAuditLogs(dto);
    }

    public String getSystemIpAddress() {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            return localHost.getHostAddress();
        } catch (UnknownHostException e) {
            return "127.0.0.1";
        }
    }
}

package com.cmms.logistics.controller;

import com.cmms.logistics.dto.NotificationsRequestDto;
import com.cmms.logistics.dto.NotificationsResponseDto;
import com.cmms.logistics.service.NotificationsService;
import com.cmms.logistics.user_context.RequireRole;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/logistics/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationsService notificationsService;
    @PostMapping
    @RequireRole("ROLE_ADMIN")
    public ResponseEntity<NotificationsResponseDto> createVehicleDelivery(@RequestBody(required = true) NotificationsRequestDto requestDto) {
        NotificationsResponseDto savedNotifications = notificationsService.saveNotifications(requestDto);
        return new ResponseEntity<>(savedNotifications, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @RequireRole("ROLE_ADMIN")
    public ResponseEntity<NotificationsResponseDto> getNotificationsById(@PathVariable Long id) {
        NotificationsResponseDto notifications = notificationsService.getById(id);
        return ResponseEntity.ok(notifications);
    }


    @GetMapping
    @RequireRole("ROLE_ADMIN")
    public ResponseEntity<List<NotificationsResponseDto>> getAllNotifications() {
        return ResponseEntity.ok(notificationsService.getAll());
    }

    @PutMapping("/{id}")
    @RequireRole("ROLE_ADMIN")
    public ResponseEntity<NotificationsResponseDto> updateNotifications(
            @PathVariable Long id,
            @Valid @RequestBody NotificationsRequestDto requestDto) {
        NotificationsResponseDto updatedNotifications= notificationsService.updateNotifications(id, requestDto);
        return ResponseEntity.ok(updatedNotifications);
    }

    @DeleteMapping("/{id}")
    @RequireRole("ROLE_ADMIN")
    public ResponseEntity<Void> deleteNotifications(@PathVariable Long id) {
        notificationsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

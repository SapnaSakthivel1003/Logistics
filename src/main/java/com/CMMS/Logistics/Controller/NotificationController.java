package com.CMMS.Logistics.Controller;

import com.CMMS.Logistics.Dto.NotificationsRequestDto;
import com.CMMS.Logistics.Dto.NotificationsResponseDto;
import com.CMMS.Logistics.Service.NotificationsService;
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
    public ResponseEntity<NotificationsResponseDto> createVehicleDelivery(@RequestBody(required = true) NotificationsRequestDto requestDto) {
        System.err.println(requestDto);
        NotificationsResponseDto savedNotifications = notificationsService.saveNotifications(requestDto);
        return new ResponseEntity<>(savedNotifications, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationsResponseDto> getNotificationsById(@PathVariable Long id) {
        NotificationsResponseDto notifications = notificationsService.getById(id);
        return ResponseEntity.ok(notifications);
    }


    @GetMapping
    public ResponseEntity<List<NotificationsResponseDto>> getAllNotifications() {
        return ResponseEntity.ok(notificationsService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificationsResponseDto> updateNotifications(
            @PathVariable Long id,
            @Valid @RequestBody NotificationsRequestDto requestDto) {
        NotificationsResponseDto updatedNotifications= notificationsService.updateNotifications(id, requestDto);
        return ResponseEntity.ok(updatedNotifications);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotifications(@PathVariable Long id) {
        notificationsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

package com.cmms.logistics.controller;

import com.cmms.logistics.dto.NotificationsRequestDto;
import com.cmms.logistics.dto.NotificationsResponseDto;
import com.cmms.logistics.exception_handler.ApiResponse;
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
    public ResponseEntity<ApiResponse<NotificationsResponseDto>> createVehicleDelivery(@RequestBody(required = true) NotificationsRequestDto requestDto) {
        NotificationsResponseDto savedNotifications = notificationsService.saveNotifications(requestDto);
        ApiResponse<NotificationsResponseDto> response = ApiResponse.success(
                HttpStatus.CREATED.value(),
                "Notification created successfully.",
                savedNotifications
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @RequireRole("ROLE_ADMIN")
    public ResponseEntity<ApiResponse<NotificationsResponseDto>> getNotificationsById(@PathVariable Long id) {
        NotificationsResponseDto notifications = notificationsService.getById(id);
        ApiResponse<NotificationsResponseDto> response = ApiResponse.success(
                HttpStatus.OK.value(),
                "Notification retrieved successfully.",
                notifications
        );
        return ResponseEntity.ok(response);
    }


    @GetMapping
    @RequireRole("ROLE_ADMIN")
    public ResponseEntity<ApiResponse<List<NotificationsResponseDto>>> getAllNotifications() {
        List<NotificationsResponseDto> customer = notificationsService.getAll();
        ApiResponse<List<NotificationsResponseDto>> response = ApiResponse.success(
                HttpStatus.OK.value(),
                "All Notification retrieved successfully.",
                customer
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @RequireRole("ROLE_ADMIN")
    public ResponseEntity<ApiResponse<NotificationsResponseDto>> updateNotifications(
            @PathVariable Long id,
            @Valid @RequestBody NotificationsRequestDto requestDto) {
        NotificationsResponseDto updatedNotifications= notificationsService.updateNotifications(id, requestDto);
        ApiResponse<NotificationsResponseDto> response = ApiResponse.success(
                HttpStatus.OK.value(),
                "Notification updated successfully.",
                updatedNotifications
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @RequireRole("ROLE_ADMIN")
    public ResponseEntity<ApiResponse<Void>> deleteNotifications(@PathVariable Long id) {
        notificationsService.deleteById(id);
        ApiResponse<Void> response = ApiResponse.success(
                HttpStatus.OK.value(),
                "Notification deleted successfully.",
                null
        );
        return ResponseEntity.ok(response);
    }
}

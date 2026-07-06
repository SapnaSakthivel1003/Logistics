package com.cmms.logistics.service;

import com.cmms.logistics.dto.NotificationsRequestDto;
import com.cmms.logistics.dto.NotificationsResponseDto;

import java.util.List;

public interface NotificationsService {
    NotificationsResponseDto saveNotifications(NotificationsRequestDto requestDto);
    NotificationsResponseDto getById(Long id);
    List<NotificationsResponseDto> getAll();
    NotificationsResponseDto updateNotifications(Long id, NotificationsRequestDto requestDto);
    void deleteById(Long id);
}

package com.CMMS.Logistics.Service;

import com.CMMS.Logistics.Dto.NotificationsRequestDto;
import com.CMMS.Logistics.Dto.NotificationsResponseDto;

import java.util.List;

public interface NotificationsService {
    NotificationsResponseDto saveNotifications(NotificationsRequestDto requestDto);
    NotificationsResponseDto getById(Long id);
    List<NotificationsResponseDto> getAll();
    NotificationsResponseDto updateNotifications(Long id, NotificationsRequestDto requestDto);
    void deleteById(Long id);
}

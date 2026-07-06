package com.cmms.logistics.repository;

import com.cmms.logistics.entity.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notifications,Long> {
}

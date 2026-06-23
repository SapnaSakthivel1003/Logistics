package com.CMMS.Logistics.Repository;

import com.CMMS.Logistics.Entity.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notifications,Long> {
}

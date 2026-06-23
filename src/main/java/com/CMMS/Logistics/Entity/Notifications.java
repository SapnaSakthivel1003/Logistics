package com.CMMS.Logistics.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name="Notifications")
@Data
public class Notifications {
    @Id
    private long id;

    private RecipientRole recipient_role;
    @Lob
    @Column(columnDefinition = "text")
    private String message;
    private NotificationType notification_type;
    private boolean is_read;
    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime created_at;
    private Long created_By;
    private LocalDateTime last_modified_at;
    private Long last_modified_by;

}

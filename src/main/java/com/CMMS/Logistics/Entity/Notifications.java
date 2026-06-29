package com.CMMS.Logistics.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name="Notifications")
@Data
public class Notifications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "recipient_role", nullable = false)
    private RecipientRole recipientRole;

    @NotNull
    @Lob
    @Column(name = "message", nullable = false, columnDefinition = "text")
    private String message;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "notification_type", nullable = false)
    private NotificationType notificationType;

    @Column(name = "is_read", nullable = false)
    private boolean isRead = false;

    // Audit Fields
    @NotNull
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @NotNull
    @Column(name = "created_by", nullable = false, updatable = false)
    private Long createdBy;

    @NotNull
    @Column(name = "last_modified_at", nullable = false)
    private LocalDateTime lastModifiedAt;

    @NotNull
    @Column(name = "last_modified_by", nullable = false)
    private Long lastModifiedBy;

}

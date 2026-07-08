package com.cmms.logistics.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.SoftDelete;

import java.time.LocalDateTime;

@Entity
@Table(name="Notifications",schema = "CmmsTables")
@Data
@SoftDelete(columnName = "is_delete")
public class Notifications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecipientRole recipientRole;

    @NotNull
    @Column(columnDefinition = "text")
    private String message;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType notificationType;

    @Column(nullable = false)
    private boolean isRead = false;
    @NotNull
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @NotNull
    @Column( updatable = false)
    private Long createdBy;

    @NotNull
    private LocalDateTime lastModifiedAt;

    @NotNull
    private Long lastModifiedBy;

}

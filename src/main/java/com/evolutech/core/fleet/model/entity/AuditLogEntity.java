package com.evolutech.core.fleet.model.entity;

import com.evolutech.core.fleet.model.utils.enums.AuditAction;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "audit_log",
    indexes = {
        @Index(name = "idx_audit_entity_type", columnList = "entity_type"),
        @Index(name = "idx_audit_entity_id", columnList = "entity_id"),
        @Index(name = "idx_audit_action", columnList = "action"),
        @Index(name = "idx_audit_timestamp", columnList = "timestamp"),
        @Index(name = "idx_audit_user_id", columnList = "user_id")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AuditLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private String id;
    @Column(nullable = false, length = 50)
    private String entityType;
    @Column(nullable = false, length = 36)
    private String entityId;
    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private AuditAction action;
    @Column(length = 100)
    private String fieldName;
    @Column(columnDefinition = "TEXT")
    private String oldValue;
    @Column(columnDefinition = "TEXT")
    private String newValue;
    @Column(length = 100)
    private String userId;
    @Column(nullable = false)
    private LocalDateTime timestamp;
    @Column(length = 100)
    private String ipAddress;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}

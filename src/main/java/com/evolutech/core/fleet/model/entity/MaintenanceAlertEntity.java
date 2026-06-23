package com.evolutech.core.fleet.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "maintenance_alert",
    indexes = {
        @Index(name = "idx_alert_vehicle_id", columnList = "vehicle_id"),
        @Index(name = "idx_alert_maintenance_id", columnList = "maintenance_id"),
        @Index(name = "idx_alert_active", columnList = "is_active")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MaintenanceAlertEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private VehicleEntity vehicle;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maintenance_id", nullable = false)
    private MaintenanceEntity maintenance;
    @Column(nullable = false, length = 200)
    private String message;
    @Column(nullable = false)
    private Boolean isActive;
    @Column(nullable = false, length = 50)
    private String alertType;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime resolvedAt;
}

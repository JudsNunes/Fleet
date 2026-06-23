package com.evolutech.core.fleet.model.entity;

import com.evolutech.core.fleet.model.utils.enums.MaintenanceStatus;
import com.evolutech.core.fleet.model.utils.enums.MaintenanceType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "maintenance",
    indexes = {
        @Index(name = "idx_vehicle_id", columnList = "vehicle_id"),
        @Index(name = "idx_maintenance_date", columnList = "maintenance_date"),
        @Index(name = "idx_done_status", columnList = "status"),
        @Index(name = "idx_vehicle_id_done", columnList = "vehicle_id,status"),
        @Index(name = "idx_created_at", columnList = "created_at"),
        @Index(name = "idx_deleted_at", columnList = "deleted_at")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MaintenanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private String id;

    @NotNull
    @Column(nullable = false)
    private LocalDate maintenanceDate;

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private MaintenanceType type;

    @Positive
    @Column(nullable = false)
    private Double cost;

    @Positive
    @Column(nullable = false)
    private Double mileage;

    @Positive
    @Column(nullable = false)
    private Double nextMileage;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private MaintenanceStatus maintenanceStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private VehicleEntity vehicle;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(insertable = false)
    private LocalDateTime deletedAt;
}

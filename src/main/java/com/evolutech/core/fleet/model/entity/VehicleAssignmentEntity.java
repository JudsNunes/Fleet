package com.evolutech.core.fleet.model.entity;

import com.evolutech.core.fleet.model.utils.enums.AssignmentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "vehicle_assignment",
    indexes = {
        @Index(name = "idx_va_vehicle_id", columnList = "vehicle_id"),
        @Index(name = "idx_va_driver_id", columnList = "driver_id"),
        @Index(name = "idx_va_status", columnList = "status"),
        @Index(name = "idx_va_start_date", columnList = "start_date"),
        @Index(name = "idx_va_deleted_at", columnList = "deleted_at"),
        @Index(name = "idx_va_created_at", columnList = "created_at")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class VehicleAssignmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    @NotNull
    private VehicleEntity vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false)
    @NotNull
    private DriverEntity driver;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    @NotNull
    private AssignmentStatus status;

    @Column(name = "assigned_by", nullable = false, length = 100)
    @NotNull
    private String assignedBy;

    @Column(length = 500)
    private String notes;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(insertable = false)
    private LocalDateTime deletedAt;
}

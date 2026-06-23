package com.evolutech.core.fleet.model.entity;

import com.evolutech.core.fleet.model.utils.enums.ServiceOrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "service_order",
    indexes = {
        @Index(name = "idx_so_vehicle_id", columnList = "vehicle_id"),
        @Index(name = "idx_so_status", columnList = "status"),
        @Index(name = "idx_so_created_at", columnList = "created_at"),
        @Index(name = "idx_so_deleted_at", columnList = "deleted_at")
    }
)
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ServiceOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    @NotNull
    private VehicleEntity vehicle;
    @Column(nullable = false, length = 500)
    @NotBlank
    private String description;
    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    @NotNull
    private ServiceOrderStatus status;
    @Column(length = 100)
    private String approvedBy;
    @Column
    private LocalDateTime approvedAt;
    @Column
    private LocalDateTime warrantyExpiryDate;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    @Column(insertable = false)
    private LocalDateTime deletedAt;
}

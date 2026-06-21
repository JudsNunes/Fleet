package com.evolutech.core.fleet.model.entity;

import com.evolutech.core.fleet.model.utils.enums.ManutentionDoneStatus;
import com.evolutech.core.fleet.model.utils.enums.typeCost;
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
    name = "manutention",
    indexes = {
        @Index(name = "idx_vehicle_id", columnList = "vehicle_id"),
        @Index(name = "idx_manutention_date", columnList = "manutention_date"),
        @Index(name = "idx_done_status", columnList = "done"),
        @Index(name = "idx_vehicle_id_done", columnList = "vehicle_id,done"),
        @Index(name = "idx_created_at", columnList = "created_at"),
        @Index(name = "idx_deleted_at", columnList = "deleted_at")
    }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ManutentionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull
    @Column(nullable = false)
    private LocalDate manutentionDate;

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private typeCost type;

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
    private ManutentionDoneStatus done = ManutentionDoneStatus.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "vehicle_id",
        nullable = false,
        foreignKey = @ForeignKey(
            name = "fk_vehicle_id",
            constraintMode = ConstraintMode.CONSTRAINT
        )
    )
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

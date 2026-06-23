package com.evolutech.core.fleet.model.entity;

import com.evolutech.core.fleet.model.utils.enums.FineStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "fine",
    indexes = {
        @Index(name = "idx_fine_vehicle_id", columnList = "vehicle_id"),
        @Index(name = "idx_fine_driver_cpf", columnList = "driver_cpf"),
        @Index(name = "idx_fine_status", columnList = "status"),
        @Index(name = "idx_fine_infraction_date", columnList = "infraction_date"),
        @Index(name = "idx_fine_created_at", columnList = "created_at")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    @NotNull
    private VehicleEntity vehicle;
    @NotBlank
    @Column(nullable = false, length = 14)
    private String driverCpf;
    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;
    @Positive
    @Column(nullable = false)
    private Double amount;
    @NotNull
    @Column(nullable = false)
    private LocalDate infractionDate;
    @Column
    private Integer points;
    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    @NotNull
    private FineStatus status;
    @Column(length = 100)
    private String costCenterId;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}

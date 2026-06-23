package com.evolutech.core.fleet.model.entity;

import com.evolutech.core.fleet.infra.AuditEntityListener;
import com.evolutech.core.fleet.model.utils.enums.FuelType;
import com.evolutech.core.fleet.model.utils.enums.VehicleStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners({AuditingEntityListener.class, AuditEntityListener.class})
@Table(
    name = "vehicle",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "plate", name = "uk_plate"),
        @UniqueConstraint(columnNames = "chassis", name = "uk_chassis"),
        @UniqueConstraint(columnNames = "renavam", name = "uk_renavam")
    },
    indexes = {
        @Index(name = "idx_status", columnList = "status"),
        @Index(name = "idx_deleted_at", columnList = "deleted_at"),
        @Index(name = "idx_created_at", columnList = "created_at")
    }
)
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class VehicleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private String id;
    @Column(unique = true, nullable = false, length = 10)
    @NotBlank
    private String plate;
    @Column(nullable = false, length = 100)
    @NotBlank
    private String model;
    @Column(nullable = false, length = 100)
    @NotBlank
    private String brand;
    @Column(nullable = false)
    @NotNull
    private Integer year;
    @Column(length = 50)
    private String color;
    @Column(columnDefinition = "DOUBLE DEFAULT 0.0")
    private Double mileage;
    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private VehicleStatus status;
    @Column(unique = true, nullable = false, length = 17)
    @NotBlank
    private String chassis;
    @Column(unique = true, nullable = false, length = 11)
    @NotBlank
    private String renavam;
    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    @NotNull
    private FuelType fuelType;
    @Column(columnDefinition = "DOUBLE")
    private Double cargoCapacityKg;
    @Column
    private Integer passengerCapacity;
    @Column(length = 50)
    private String engineType;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    @Column(insertable = false)
    private LocalDateTime deletedAt;
}

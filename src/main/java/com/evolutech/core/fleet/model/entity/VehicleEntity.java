package com.evolutech.core.fleet.model.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "vehicle",
    uniqueConstraints = @UniqueConstraint(columnNames = "plate", name = "uk_plate"),
    indexes = {
        @Index(name = "idx_status", columnList = "status"),
        @Index(name = "idx_deleted_at", columnList = "deleted_at"),
        @Index(name = "idx_created_at", columnList = "created_at")
    }
)
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class VehicleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

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
    private String vehicleStatus;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(insertable = false)
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ManutentionEntity> maintenances;
}

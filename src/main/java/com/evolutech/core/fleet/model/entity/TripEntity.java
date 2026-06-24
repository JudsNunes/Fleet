package com.evolutech.core.fleet.model.entity;

import com.evolutech.core.fleet.model.utils.enums.TripStatus;
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
    name = "trip",
    indexes = {
        @Index(name = "idx_trip_vehicle_id", columnList = "vehicle_id"),
        @Index(name = "idx_trip_driver_id", columnList = "driver_id"),
        @Index(name = "idx_trip_status", columnList = "status"),
        @Index(name = "idx_trip_departure_date", columnList = "departure_date"),
        @Index(name = "idx_trip_deleted_at", columnList = "deleted_at"),
        @Index(name = "idx_trip_created_at", columnList = "created_at")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TripEntity {

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

    @NotBlank
    @Column(nullable = false, length = 200)
    private String description;

    @NotBlank
    @Column(nullable = false, length = 150)
    private String origin;

    @NotBlank
    @Column(nullable = false, length = 150)
    private String destination;

    @Column(name = "planned_distance_km")
    private Double plannedDistanceKm;

    @Column(name = "actual_distance_km")
    private Double actualDistanceKm;

    @Column(name = "departure_date", nullable = false)
    @NotNull
    private LocalDateTime departureDate;

    @Column(name = "arrival_date")
    private LocalDateTime arrivalDate;

    @Column(name = "start_mileage")
    private Double startMileage;

    @Column(name = "end_mileage")
    private Double endMileage;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    @NotNull
    private TripStatus status;

    @Column(name = "route_deviation", nullable = false)
    private Boolean routeDeviation = false;

    @Column(name = "deviation_justification", length = 500)
    private String deviationJustification;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(insertable = false)
    private LocalDateTime deletedAt;
}

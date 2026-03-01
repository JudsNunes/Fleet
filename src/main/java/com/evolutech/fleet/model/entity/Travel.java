package com.evolutech.fleet.model.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "TBG_TRAVEL")
public record Travel(
        @Id @GeneratedValue(strategy = GenerationType.UUID)
        Long id,
        String origin,
        String destination,
        Double fuelConsumed,
        Double distanceTraveled,
        Double  mileage,
        Date travelDate,
        Long vehicleId,
        Long driverId
) {
}

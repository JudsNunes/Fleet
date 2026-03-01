package com.evolutech.fleet.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "TBG_COST")
public record Cost(
        @Id @GeneratedValue(strategy = GenerationType.UUID)
        Long id,
        String typeCost,
        String descriptionCost,
        Double valueCost,
        Long vehicleId,
        Long driverId
) {
}

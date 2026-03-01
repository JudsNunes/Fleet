package com.evolutech.fleet.model.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "TBG_MANUTETION")
public record Manutetion(
        @Id @GeneratedValue(strategy = GenerationType.UUID)
        Long id,
        Date manutetionDate,
        String descriptionManutention,
        String typeManutention,
        Double costManutention,
        Double mileageManutention,
        Double nextManutentionMileage,
        String doneManutention,
        Long vehicleId
) {
}

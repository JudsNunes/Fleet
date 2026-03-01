package com.evolutech.fleet.model.entity;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.UniqueElements;

@Entity
@Table(name = "TBG_VEHICLE" ,uniqueConstraints = @UniqueConstraint(columnNames = "plate"))
public record Vehicle(
        @Id @GeneratedValue(strategy = GenerationType.UUID)
        Long id,
        @UniqueElements
        String plate,
        String model,
        String brand,
        Integer year,
        String color
) {
}

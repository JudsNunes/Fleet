package com.evolutech.fleet.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "TBG_DRIVER")
public record Driver(
        @Id @GeneratedValue(strategy = GenerationType.UUID)
        Long id,
        String name,
        String PhoneNumber,
        String Email,
        String documentNumber,
        boolean EAR,
        String toxicologicalExam,
        String driverLicenseStatus,
        boolean disponible
) {
}

package com.evolutech.fleet.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public record Vehicle(
        @Id @GeneratedValue(strategy = GenerationType.UUID)
        Long id,
        String Name,
        Boolean Cnh,
        String phoneNumber,
        String plate
) {
}

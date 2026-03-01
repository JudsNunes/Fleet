package com.evolutech.fleet.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "TBG_VEHICLE", uniqueConstraints = @UniqueConstraint(columnNames = "plate"))
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(unique = true, nullable = false)
    @NotBlank
    @Pattern(regexp = "[A-Z]{3}-\\d{4}", message = "Placa deve estar no formato ABC-1234")
    private String plate;
    @NotBlank
    private String model;
    @NotBlank
    private String brand;
    @NotNull
    private Integer year;
    private String color;
    private Double mileage;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

}

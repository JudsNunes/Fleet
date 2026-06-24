package com.evolutech.core.fleet.model.entity;

import com.evolutech.core.fleet.model.utils.enums.DriverLicenseCategory;
import com.evolutech.core.fleet.model.utils.enums.DriverLicenseStatus;
import com.evolutech.core.fleet.model.utils.enums.DriverStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "driver",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "cpf", name = "uk_driver_cpf"),
        @UniqueConstraint(columnNames = "cnh_number", name = "uk_driver_cnh")
    },
    indexes = {
        @Index(name = "idx_driver_cpf", columnList = "cpf"),
        @Index(name = "idx_driver_cnh_status", columnList = "cnh_status"),
        @Index(name = "idx_driver_status", columnList = "status"),
        @Index(name = "idx_driver_deleted_at", columnList = "deleted_at"),
        @Index(name = "idx_driver_created_at", columnList = "created_at")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DriverEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private String id;

    @NotBlank
    @Column(nullable = false, length = 150)
    private String name;

    @NotBlank
    @Column(unique = true, nullable = false, length = 14)
    private String cpf;

    @NotBlank
    @Column(name = "cnh_number", unique = true, nullable = false, length = 11)
    private String cnhNumber;

    @NotBlank
    @Column(name = "cnh_category", nullable = false, length = 5)
    @Enumerated(EnumType.STRING)
    private DriverLicenseCategory cnhCategory;

    @NotNull
    @Column(name = "cnh_expiry_date", nullable = false)
    private LocalDate cnhExpiryDate;

    @NotNull
    @Column(name = "cnh_status", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private DriverLicenseStatus cnhStatus;

    @Column(length = 15)
    private String phone;

    @Column(length = 100)
    private String email;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(length = 200)
    private String address;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    @NotNull
    private DriverStatus status;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(insertable = false)
    private LocalDateTime deletedAt;
}

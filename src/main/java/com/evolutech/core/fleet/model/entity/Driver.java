package com.evolutech.core.fleet.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "TBG_DRIVER")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    @Pattern(regexp = "\\d{11}", message = "Telefone deve ter 11 dígitos")
    private String phoneNumber;

    @Email
    private String email;

    @NotBlank
    private String documentNumber;

    private boolean ear; // Exame de aptidão

    private String toxicologicalExam;

    private String driverLicenseStatus;

    private boolean disponible;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    // Getters e Setters (ou Lombok @Getter/@Setter)
}

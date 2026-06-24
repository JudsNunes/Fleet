package com.evolutech.core.fleet.repository;

import com.evolutech.core.fleet.model.entity.DriverEntity;
import com.evolutech.core.fleet.model.utils.enums.DriverLicenseStatus;
import com.evolutech.core.fleet.model.utils.enums.DriverStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<DriverEntity, String> {

    @Query("SELECT d FROM DriverEntity d WHERE d.deletedAt IS NULL AND d.cpf = :cpf")
    Optional<DriverEntity> findByCpfAndNotDeleted(@Param("cpf") String cpf);

    @Query("SELECT d FROM DriverEntity d WHERE d.deletedAt IS NULL AND d.cnhNumber = :cnhNumber")
    Optional<DriverEntity> findByCnhNumberAndNotDeleted(@Param("cnhNumber") String cnhNumber);

    @Query("SELECT d FROM DriverEntity d WHERE d.deletedAt IS NULL ORDER BY d.name ASC")
    Page<DriverEntity> findAllActive(Pageable pageable);

    @Query("SELECT d FROM DriverEntity d WHERE d.deletedAt IS NULL AND d.status = :status ORDER BY d.name ASC")
    Page<DriverEntity> findByStatusAndNotDeleted(@Param("status") DriverStatus status, Pageable pageable);

    @Query("SELECT d FROM DriverEntity d WHERE d.deletedAt IS NULL AND d.cnhStatus = :cnhStatus ORDER BY d.name ASC")
    Page<DriverEntity> findByCnhStatusAndNotDeleted(@Param("cnhStatus") DriverLicenseStatus cnhStatus, Pageable pageable);

    @Query("SELECT d FROM DriverEntity d WHERE d.deletedAt IS NULL AND d.cnhExpiryDate <= :expiryDate ORDER BY d.cnhExpiryDate ASC")
    List<DriverEntity> findExpiringCnhs(@Param("expiryDate") LocalDate expiryDate);

    @Query("SELECT d FROM DriverEntity d WHERE d.deletedAt IS NULL " +
           "AND (:name IS NULL OR d.name LIKE CONCAT('%', :name, '%')) " +
           "AND (:cpf IS NULL OR d.cpf LIKE CONCAT('%', :cpf, '%')) " +
           "AND (:status IS NULL OR d.status = :status) " +
           "AND (:cnhStatus IS NULL OR d.cnhStatus = :cnhStatus) " +
           "ORDER BY d.name ASC")
    Page<DriverEntity> findByFilters(
            @Param("name") String name,
            @Param("cpf") String cpf,
            @Param("status") DriverStatus status,
            @Param("cnhStatus") DriverLicenseStatus cnhStatus,
            Pageable pageable);

    @Query("SELECT COUNT(d) FROM DriverEntity d WHERE d.deletedAt IS NULL")
    long countActive();
}

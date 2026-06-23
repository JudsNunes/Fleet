package com.evolutech.core.fleet.repository;

import com.evolutech.core.fleet.model.entity.ServiceOrderEntity;
import com.evolutech.core.fleet.model.utils.enums.ServiceOrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceOrderRepository extends JpaRepository<ServiceOrderEntity, String> {

    @Query("SELECT so FROM ServiceOrderEntity so WHERE so.deletedAt IS NULL AND so.vehicle.id = :vehicleId ORDER BY so.createdAt DESC")
    Page<ServiceOrderEntity> findByVehicleIdAndNotDeleted(@Param("vehicleId") String vehicleId, Pageable pageable);

    @Query("SELECT so FROM ServiceOrderEntity so WHERE so.deletedAt IS NULL AND so.status = :status ORDER BY so.createdAt DESC")
    Page<ServiceOrderEntity> findByStatusAndNotDeleted(@Param("status") ServiceOrderStatus status, Pageable pageable);

    @Query("SELECT so FROM ServiceOrderEntity so WHERE so.deletedAt IS NULL ORDER BY so.createdAt DESC")
    Page<ServiceOrderEntity> findAllActive(Pageable pageable);

    @Query("SELECT so FROM ServiceOrderEntity so WHERE so.deletedAt IS NULL AND so.vehicle.id = :vehicleId AND so.status = :status")
    List<ServiceOrderEntity> findByVehicleIdAndStatus(@Param("vehicleId") String vehicleId, @Param("status") ServiceOrderStatus status);

    @Query("SELECT so FROM ServiceOrderEntity so WHERE so.deletedAt IS NULL AND so.vehicle.id = :vehicleId AND so.warrantyExpiryDate > :now")
    List<ServiceOrderEntity> findActiveWarrantiesByVehicle(@Param("vehicleId") String vehicleId, @Param("now") LocalDateTime now);
}

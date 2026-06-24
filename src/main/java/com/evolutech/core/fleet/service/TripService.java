package com.evolutech.core.fleet.service;

import com.evolutech.core.fleet.model.dto.request.TripRequestDTO;
import com.evolutech.core.fleet.model.dto.response.TripResponseDTO;
import com.evolutech.core.fleet.model.utils.enums.TripStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TripService {

    Optional<TripResponseDTO> findById(String id);

    TripResponseDTO save(TripRequestDTO body);

    TripResponseDTO update(String id, TripRequestDTO body);

    TripResponseDTO start(String id, Double startMileage);

    TripResponseDTO complete(String id, Double endMileage);

    TripResponseDTO cancel(String id);

    TripResponseDTO registerDeviation(String id, String justification);

    void delete(String id);

    Page<TripResponseDTO> findAllPaged(Pageable pageable);

    Page<TripResponseDTO> findByFilters(String vehicleId, String driverId, TripStatus status, Pageable pageable);

    Page<TripResponseDTO> findByVehicleId(String vehicleId, Pageable pageable);

    Page<TripResponseDTO> findByDriverId(String driverId, Pageable pageable);
}

package com.evolutech.core.fleet.API;

import com.evolutech.core.fleet.exception.BusinessException;
import com.evolutech.core.fleet.model.dto.request.VehicleRequestDTO;
import com.evolutech.core.fleet.service.VehicleService;
import com.evolutech.fleet.api.VehiclesApi;
import com.evolutech.fleet.api.model.VehicleDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class VehicleController implements VehiclesApi {

    private final VehicleService vehicleService;

    @Override
    public ResponseEntity<VehicleDTO> createVehicle(@Valid @RequestBody VehicleRequestDTO vehicleRequestDTO) {
        log.info("Creating new vehicle with plate: {}", vehicleRequestDTO.getPlate());
        try {
            var responseDTO = vehicleService.save(vehicleRequestDTO);
            log.debug("Vehicle created successfully with ID: {}", responseDTO.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (BusinessException e) {
            log.error("Business error creating vehicle: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (IllegalArgumentException e) {
            log.error("Invalid data for vehicle creation: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            log.error("Error creating vehicle: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<VehicleDTO> getVehicleById(@PathVariable Long id) {
        log.info("Fetching vehicle with ID: {}", id);
        try {
            var vehicle = vehicleService.findById(id);
            if (vehicle.isPresent()) {
                log.debug("Vehicle found with ID: {}", id);
                return ResponseEntity.ok(vehicle.get());
            }
            log.warn("Vehicle not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        } catch (BusinessException e) {
            log.error("Business error fetching vehicle: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            log.error("Error fetching vehicle with ID: {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Page<VehicleDTO>> getAllVehicles(
            @PageableDefault(size = 20, page = 0) Pageable pageable) {
        log.info("Fetching vehicles with pagination: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        try {
            var vehicles = vehicleService.findAllPaged(pageable);
            log.debug("Found {} vehicles in page {}", vehicles.getNumberOfElements(), pageable.getPageNumber());
            return ResponseEntity.ok(vehicles);
        } catch (Exception e) {
            log.error("Error fetching all vehicles: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<VehicleDTO> updateVehicle(@PathVariable Long id, @Valid @RequestBody VehicleRequestDTO vehicleRequestDTO) {
        log.info("Updating vehicle with ID: {}", id);
        try {
            var updatedVehicle = vehicleService.update(vehicleRequestDTO);
            log.debug("Vehicle with ID: {} updated successfully", id);
            return ResponseEntity.ok(updatedVehicle);
        } catch (BusinessException e) {
            log.warn("Vehicle not found for update with ID: {}", id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (IllegalArgumentException e) {
            log.error("Invalid data for vehicle update: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            log.error("Error updating vehicle with ID: {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        log.info("Deleting vehicle with ID: {}", id);
        try {
            vehicleService.delete(id);
            log.debug("Vehicle with ID: {} deleted successfully", id);
            return ResponseEntity.noContent().build();
        } catch (BusinessException e) {
            log.warn("Vehicle not found for deletion with ID: {}", id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            log.error("Error deleting vehicle with ID: {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<List<VehicleDTO>> findByIdAndPlate(
            @RequestParam Long id,
            @RequestParam String plate) {
        log.info("Searching vehicle by ID: {} and plate: {}", id, plate);
        try {
            var vehicle = vehicleService.findById(id);
            if (vehicle.isPresent() && vehicle.get().getPlate().equalsIgnoreCase(plate)) {
                log.debug("Vehicle found with ID: {} and plate: {}", id, plate);
                return ResponseEntity.ok(List.of(vehicle.get()));
            }
            log.warn("Vehicle not found with ID: {} and plate: {}", id, plate);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error searching vehicle with ID: {} and plate: {}: {}", id, plate, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

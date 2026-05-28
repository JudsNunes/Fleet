package com.evolutech.core.fleet.API;

import com.evolutech.core.fleet.exception.BusinessException;
import com.evolutech.core.fleet.model.dto.request.VehicleRequestDTO;
import com.evolutech.core.fleet.service.VehicleService;
import com.evolutech.fleet.api.VehiclesApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
@Slf4j
public class VehicleController implements VehiclesApi {

    private final VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<VehicleResponseDTO> createVehicle(@RequestBody VehicleRequestDTO vehicleRequestDTO) {
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

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> getVehicleById(@PathVariable Long id) {
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

    @GetMapping
    public ResponseEntity<List<VehicleResponseDTO>> getAllVehicles() {
        log.info("Fetching all vehicles");
        try {
            var vehicles = vehicleService.findAll();
            log.debug("Found {} vehicles", vehicles.size());
            return ResponseEntity.ok(vehicles);
        } catch (Exception e) {
            log.error("Error fetching all vehicles: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> updateVehicle(
            @PathVariable Long id,
            @RequestBody VehicleRequestDTO vehicleRequestDTO) {
        log.info("Updating vehicle with ID: {}", id);
        try {
            if (vehicleService.findById(id).isEmpty()) {
                log.warn("Vehicle not found with ID: {} for update", id);
                return ResponseEntity.notFound().build();
            }
            var responseDTO = vehicleService.update(vehicleRequestDTO);
            log.debug("Vehicle with ID: {} updated successfully", id);
            return ResponseEntity.ok(responseDTO);
        } catch (IllegalArgumentException e) {
            log.error("Invalid data for vehicle update: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (BusinessException e) {
            log.error("Business error updating vehicle: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            log.error("Error updating vehicle with ID: {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
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

    @GetMapping("/search")
    public ResponseEntity<List<VehicleResponseDTO>> findByIdAndPlate(
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

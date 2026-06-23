package com.evolutech.core.fleet.API;

import com.evolutech.core.fleet.exception.BusinessException;
import com.evolutech.core.fleet.model.dto.request.ManutentionRequestDTO;
import com.evolutech.core.fleet.model.dto.response.ManutentionResponseDTO;
import com.evolutech.core.fleet.service.ManutentionService;
import com.evolutech.fleet.api.MaintenancesApi;
import com.evolutech.fleet.api.model.MaintenanceDTO;
import com.evolutech.fleet.api.model.MaintenanceRequestDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ManutentionController implements MaintenancesApi {

    private final ManutentionService manutentionService;


    @Override
    public ResponseEntity<MaintenanceDTO> createMaintenance(@Valid @RequestBody MaintenanceRequestDTO manutentionRequestDTO) {
        log.info("Creating new maintenance record with description: {}", manutentionRequestDTO.getDescription());
        try {
            var responseDTO = manutentionService.save(manutentionRequestDTO);
            log.debug("Maintenance record created successfully with ID: {}", responseDTO.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToMaintenanceDTO(responseDTO));
        } catch (BusinessException e) {
            log.error("Business error creating maintenance: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (IllegalArgumentException e) {
            log.error("Invalid data for maintenance creation: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            log.error("Error creating maintenance: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<MaintenanceDTO> getMaintenanceById(@PathVariable Long id) {
        log.info("Fetching maintenance record with ID: {}", id);
        try {
            var maintenance = manutentionService.findById(id);
            if (maintenance.isPresent()) {
                log.debug("Maintenance record found with ID: {}", id);
                return ResponseEntity.ok(convertToMaintenanceDTO(maintenance.get()));
            }
            log.warn("Maintenance record not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        } catch (BusinessException e) {
            log.error("Business error fetching maintenance: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            log.error("Error fetching maintenance with ID: {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    @Override
    public ResponseEntity<Page<MaintenanceDTO>> getAllMaintenances(
            @PageableDefault(size = 20, page = 0) Pageable pageable) {
        log.info("Fetching maintenance records with pagination: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        try {
            var maintenances = manutentionService.findAllPaged(pageable);
            log.debug("Found {} maintenance records in page {}", maintenances.getNumberOfElements(), pageable.getPageNumber());
            var dtos = maintenances.map(this::convertToMaintenanceDTO);
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            log.error("Error fetching all maintenance records: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @Override
    public ResponseEntity<List<MaintenanceDTO>> getMaintenancesByVehicleId(@PathVariable Long vehicleId) {
        log.info("Fetching maintenance records for vehicle ID: {}", vehicleId);
        try {
            var maintenances = manutentionService.findByVehicleId(vehicleId);
            log.debug("Found {} maintenance records for vehicle ID: {}", maintenances.size(), vehicleId);
            var dtos = maintenances.stream()
                    .map(this::convertToMaintenanceDTO)
                    .collect(Collectors.toList());

            if (dtos.isEmpty()) {
                log.warn("No maintenance records found for vehicle ID: {}", vehicleId);
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            log.error("Error fetching maintenance records for vehicle ID: {}: {}", vehicleId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @Override
    public ResponseEntity<List<MaintenanceDTO>> getMaintenancesByDone(
            @RequestParam Boolean done) {
        log.info("Fetching maintenance records with done status: {}", done);
        try {
            var maintenances = manutentionService.findByDone(done);
            log.debug("Found {} maintenance records with done status: {}", maintenances.size(), done);
            var dtos = maintenances.stream()
                    .map(this::convertToMaintenanceDTO)
                    .collect(Collectors.toList());

            if (dtos.isEmpty()) {
                log.warn("No maintenance records found with done status: {}", done);
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            log.error("Error fetching maintenance records with done status: {}: {}", done, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/vehicle/{vehicleId}/done")
    @Override
    public ResponseEntity<List<MaintenanceDTO>> getMaintenancesByVehicleIdAndDone(
            @PathVariable Long vehicleId,
            @RequestParam Boolean done) {
        log.info("Fetching maintenance records for vehicle ID: {} with done status: {}", vehicleId, done);
        try {
            var maintenances = manutentionService.findByVehicleIdAndDone(vehicleId, done);
            log.debug("Found {} maintenance records for vehicle ID: {} with done status: {}",
                    maintenances.size(), vehicleId, done);
            var dtos = maintenances.stream()
                    .map(this::convertToMaintenanceDTO)
                    .collect(Collectors.toList());

            if (dtos.isEmpty()) {
                log.warn("No maintenance records found for vehicle ID: {} with done status: {}", vehicleId, done);
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            log.error("Error fetching maintenance records for vehicle ID: {} with done status: {}: {}",
                    vehicleId, done, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<MaintenanceDTO> updateMaintenance(
            @PathVariable Long id,
            @Valid @RequestBody ManutentionRequestDTO manutentionRequestDTO) {
        log.info("Updating maintenance record with ID: {}", id);
        try {
            if (manutentionService.findById(id).isEmpty()) {
                log.warn("Maintenance record not found with ID: {} for update", id);
                return ResponseEntity.notFound().build();
            }
            var responseDTO = manutentionService.update(id, manutentionRequestDTO);
            log.debug("Maintenance record with ID: {} updated successfully", id);
            return ResponseEntity.ok(convertToMaintenanceDTO(responseDTO));
        } catch (IllegalArgumentException e) {
            log.error("Invalid data for maintenance update: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (BusinessException e) {
            log.error("Business error updating maintenance: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            log.error("Error updating maintenance with ID: {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> deleteMaintenance(@PathVariable Long id) {
        log.info("Deleting maintenance record with ID: {}", id);
        try {
            manutentionService.delete(id);
            log.debug("Maintenance record with ID: {} deleted successfully", id);
            return ResponseEntity.noContent().build();
        } catch (BusinessException e) {
            log.warn("Maintenance record not found for deletion with ID: {}", id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            log.error("Error deleting maintenance with ID: {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Converts ManutentionResponseDTO to MaintenanceDTO (OpenAPI generated class)
     * Handles proper type conversions for dates and status fields
     */
    private MaintenanceDTO convertToMaintenanceDTO(ManutentionResponseDTO responseDTO) {
        MaintenanceDTO dto = new MaintenanceDTO();
        dto.setId(Long.parseLong(responseDTO.getId()));
        dto.setMaintenanceDate(responseDTO.getManutentionDate());
        dto.setDescription(responseDTO.getDescription());
        dto.setCost(responseDTO.getCost());
        // done is now a String from ManutentionDoneStatus enum, convert appropriately
        dto.setDone("COMPLETED".equals(responseDTO.getDone()) || "IN_PROGRESS".equals(responseDTO.getDone()));
        dto.setVehicleId(responseDTO.getVehicleId());
        // Ensure createdAt/updatedAt are LocalDateTime
        if (responseDTO.getCreatedAt() != null) {
            dto.setCreatedAt(responseDTO.getCreatedAt());
        }
        if (responseDTO.getUpdatedAt() != null) {
            dto.setUpdatedAt(responseDTO.getUpdatedAt());
        }
        return dto;
    }
}

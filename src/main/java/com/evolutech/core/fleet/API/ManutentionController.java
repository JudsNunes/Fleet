package com.evolutech.core.fleet.API;

import com.evolutech.core.fleet.api.ManutentionApi;
import com.evolutech.core.fleet.model.dto.request.ManutentionRequestDTO;
import com.evolutech.core.fleet.model.dto.response.ManutentionResponseDTO;
import com.evolutech.core.fleet.exception.BusinessException;
import com.evolutech.core.fleet.service.ManutentionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ManutentionController implements ManutentionApi {

    private final ManutentionService manutentionService;

    @Override
    public ResponseEntity<ManutentionResponseDTO> createMaintenance(
            @RequestBody ManutentionRequestDTO manutentionRequestDTO) {
        log.info("Creating new maintenance record with description: {}", manutentionRequestDTO.getDescription());
        try {
            var responseDTO = manutentionService.save(manutentionRequestDTO);
            log.debug("Maintenance record created successfully with ID: {}", responseDTO.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (BusinessException e) {
            log.error("Business error creating maintenance record: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (IllegalArgumentException e) {
            log.error("Invalid data for maintenance record creation: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            log.error("Error creating maintenance record: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<ManutentionResponseDTO> getMaintenanceById(@PathVariable String id) {
        log.info("Fetching maintenance record with ID: {}", id);
        try {
            var maintenance = manutentionService.findById(id);
            if (maintenance.isPresent()) {
                log.debug("Maintenance record found with ID: {}", id);
                return ResponseEntity.ok(maintenance.get());
            }
            log.warn("Maintenance record not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error fetching maintenance record with ID: {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<List<ManutentionResponseDTO>> getAllMaintenance() {
        log.info("Fetching all maintenance records");
        try {
            var maintenanceList = manutentionService.findAll();
            log.debug("Found {} maintenance records", maintenanceList.size());
            return ResponseEntity.ok(maintenanceList);
        } catch (Exception e) {
            log.error("Error fetching all maintenance records: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<List<ManutentionResponseDTO>> getMaintenanceByVehicle(@PathVariable Long vehicleId) {
        log.info("Fetching maintenance records for vehicle ID: {}", vehicleId);
        try {
            var maintenanceList = manutentionService.findByVehicleId(vehicleId);
            log.debug("Found {} maintenance records for vehicle ID: {}", maintenanceList.size(), vehicleId);
            return ResponseEntity.ok(maintenanceList);
        } catch (Exception e) {
            log.error("Error fetching maintenance records for vehicle ID: {}: {}", vehicleId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<List<ManutentionResponseDTO>> getMaintenanceByVehicleAndDone(
            @PathVariable Long vehicleId,
            @RequestParam(defaultValue = "false") boolean done) {
        log.info("Fetching maintenance records for vehicle ID: {} with done status: {}", vehicleId, done);
        try {
            var maintenanceList = manutentionService.findByVehicleIdAndDone(vehicleId, done);
            log.debug("Found {} maintenance records for vehicle ID: {} with done status: {}",
                    maintenanceList.size(), vehicleId, done);
            return ResponseEntity.ok(maintenanceList);
        } catch (Exception e) {
            log.error("Error fetching maintenance records for vehicle ID: {} with done status: {}: {}",
                    vehicleId, done, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<List<ManutentionResponseDTO>> getMaintenanceByDone(
            @RequestParam(defaultValue = "false") boolean done) {
        log.info("Fetching maintenance records with done status: {}", done);
        try {
            var maintenanceList = manutentionService.findByDone(done);
            log.debug("Found {} maintenance records with done status: {}", maintenanceList.size(), done);
            return ResponseEntity.ok(maintenanceList);
        } catch (Exception e) {
            log.error("Error fetching maintenance records with done status: {}: {}", done, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<ManutentionResponseDTO> updateMaintenance(
            @PathVariable String id,
            @RequestBody ManutentionRequestDTO manutentionRequestDTO) {
        log.info("Updating maintenance record with ID: {}", id);
        try {
            if (manutentionService.findById(id).isEmpty()) {
                log.warn("Maintenance record not found with ID: {} for update", id);
                return ResponseEntity.notFound().build();
            }
            var responseDTO = manutentionService.update(id, manutentionRequestDTO);
            log.debug("Maintenance record with ID: {} updated successfully", id);
            return ResponseEntity.ok(responseDTO);
        } catch (IllegalArgumentException e) {
            log.error("Invalid data for maintenance record update: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (BusinessException e) {
            log.error("Business error updating maintenance record: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            log.error("Error updating maintenance record with ID: {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Void> deleteMaintenance(@PathVariable String id) {
        log.info("Deleting maintenance record with ID: {}", id);
        try {
            manutentionService.delete(id);
            log.debug("Maintenance record with ID: {} deleted successfully", id);
            return ResponseEntity.noContent().build();
        } catch (BusinessException e) {
            log.warn("Maintenance record not found for deletion with ID: {}", id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            log.error("Error deleting maintenance record with ID: {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

package com.evolutech.core.fleet.API;

import com.evolutech.core.fleet.model.dto.request.VehicleAssignmentRequestDTO;
import com.evolutech.core.fleet.model.dto.response.VehicleAssignmentResponseDTO;
import com.evolutech.core.fleet.model.utils.enums.AssignmentStatus;
import com.evolutech.core.fleet.service.VehicleAssignmentService;
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
@RequestMapping("/assignments")
@RequiredArgsConstructor
@Slf4j
public class VehicleAssignmentController {

    private final VehicleAssignmentService vehicleAssignmentService;

    @GetMapping("/{id}")
    public ResponseEntity<VehicleAssignmentResponseDTO> getAssignmentById(@PathVariable String id) {
        log.info("Fetching assignment: {}", id);
        return vehicleAssignmentService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<VehicleAssignmentResponseDTO> createAssignment(@Valid @RequestBody VehicleAssignmentRequestDTO body) {
        log.info("Creating assignment for vehicle: {} and driver: {}", body.getVehicleId(), body.getDriverId());
        var result = vehicleAssignmentService.save(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleAssignmentResponseDTO> updateAssignment(@PathVariable String id, @Valid @RequestBody VehicleAssignmentRequestDTO body) {
        log.info("Updating assignment: {}", id);
        var result = vehicleAssignmentService.update(id, body);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{id}/end")
    public ResponseEntity<VehicleAssignmentResponseDTO> endAssignment(@PathVariable String id) {
        log.info("Ending assignment: {}", id);
        var result = vehicleAssignmentService.end(id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable String id) {
        log.info("Deleting assignment: {}", id);
        vehicleAssignmentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<VehicleAssignmentResponseDTO>> getAllAssignments(
            @RequestParam(required = false) String vehicleId,
            @RequestParam(required = false) String driverId,
            @RequestParam(required = false) AssignmentStatus status,
            @PageableDefault(size = 20, page = 0) Pageable pageable) {
        log.info("Fetching assignments - vehicle: {}, driver: {}, status: {}", vehicleId, driverId, status);
        if (vehicleId != null) {
            return ResponseEntity.ok(vehicleAssignmentService.findByVehicleId(vehicleId, pageable));
        }
        if (driverId != null) {
            return ResponseEntity.ok(vehicleAssignmentService.findByDriverId(driverId, pageable));
        }
        if (status != null) {
            return ResponseEntity.ok(vehicleAssignmentService.findByStatus(status, pageable));
        }
        return ResponseEntity.ok(vehicleAssignmentService.findAllPaged(pageable));
    }

    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<Page<VehicleAssignmentResponseDTO>> getAssignmentsByVehicle(
            @PathVariable String vehicleId,
            @PageableDefault(size = 20, page = 0) Pageable pageable) {
        log.info("Fetching assignments by vehicle: {}", vehicleId);
        return ResponseEntity.ok(vehicleAssignmentService.findByVehicleId(vehicleId, pageable));
    }

    @GetMapping("/driver/{driverId}")
    public ResponseEntity<Page<VehicleAssignmentResponseDTO>> getAssignmentsByDriver(
            @PathVariable String driverId,
            @PageableDefault(size = 20, page = 0) Pageable pageable) {
        log.info("Fetching assignments by driver: {}", driverId);
        return ResponseEntity.ok(vehicleAssignmentService.findByDriverId(driverId, pageable));
    }

    @GetMapping("/active")
    public ResponseEntity<List<VehicleAssignmentResponseDTO>> getActiveAssignments() {
        log.info("Fetching active assignments");
        return ResponseEntity.ok(vehicleAssignmentService.findActiveAssignments());
    }
}

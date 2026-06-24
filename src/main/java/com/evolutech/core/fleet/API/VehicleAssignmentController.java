package com.evolutech.core.fleet.API;

import com.evolutech.core.fleet.mapper.ApiMapper;
import com.evolutech.core.fleet.service.VehicleAssignmentService;
import com.evolutech.fleet.api.AssignmentsApi;
import com.evolutech.fleet.api.model.VehicleAssignmentDTO;
import com.evolutech.fleet.api.model.VehicleAssignmentPageDTO;
import com.evolutech.fleet.api.model.VehicleAssignmentRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class VehicleAssignmentController implements AssignmentsApi {

    private final VehicleAssignmentService vehicleAssignmentService;
    private final ApiMapper apiMapper;

    @Override
    public ResponseEntity<VehicleAssignmentDTO> createAssignment(VehicleAssignmentRequestDTO vehicleAssignmentRequestDTO) {
        log.info("Creating assignment for vehicle: {} and driver: {}", vehicleAssignmentRequestDTO.getVehicleId(), vehicleAssignmentRequestDTO.getDriverId());
        var internalRequest = apiMapper.toAssignmentRequestInternal(vehicleAssignmentRequestDTO);
        var result = vehicleAssignmentService.save(internalRequest);
        return ResponseEntity.status(201).body(apiMapper.toAssignmentApi(result));
    }

    @Override
    public ResponseEntity<Void> deleteAssignment(UUID id) {
        log.info("Deleting assignment: {}", id);
        vehicleAssignmentService.delete(id.toString());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<VehicleAssignmentDTO> endAssignment(UUID id) {
        log.info("Ending assignment: {}", id);
        var result = vehicleAssignmentService.end(id.toString());
        return ResponseEntity.ok(apiMapper.toAssignmentApi(result));
    }

    @Override
    public ResponseEntity<List<VehicleAssignmentDTO>> getActiveAssignments() {
        log.info("Fetching active assignments");
        var result = vehicleAssignmentService.findActiveAssignments();
        return ResponseEntity.ok(result.stream().map(apiMapper::toAssignmentApi).collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<VehicleAssignmentPageDTO> getAllAssignments(UUID vehicleId, UUID driverId, String status, Integer page, Integer size) {
        log.info("Fetching assignments - page: {}, size: {}", page, size);
        var pageable = PageRequest.of(page, size);
        if (vehicleId != null) {
            return ResponseEntity.ok(apiMapper.toAssignmentPageApi(vehicleAssignmentService.findByVehicleId(vehicleId.toString(), pageable)));
        }
        if (driverId != null) {
            return ResponseEntity.ok(apiMapper.toAssignmentPageApi(vehicleAssignmentService.findByDriverId(driverId.toString(), pageable)));
        }
        if (status != null) {
            return ResponseEntity.ok(apiMapper.toAssignmentPageApi(vehicleAssignmentService.findByStatus(com.evolutech.core.fleet.model.utils.enums.AssignmentStatus.valueOf(status), pageable)));
        }
        return ResponseEntity.ok(apiMapper.toAssignmentPageApi(vehicleAssignmentService.findAllPaged(pageable)));
    }

    @Override
    public ResponseEntity<VehicleAssignmentDTO> getAssignmentById(UUID id) {
        log.info("Fetching assignment: {}", id);
        return vehicleAssignmentService.findById(id.toString())
                .map(a -> ResponseEntity.ok(apiMapper.toAssignmentApi(a)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<VehicleAssignmentPageDTO> getAssignmentsByDriver(UUID driverId, Integer page, Integer size) {
        log.info("Fetching assignments by driver: {}", driverId);
        var pageable = PageRequest.of(page, size);
        var result = vehicleAssignmentService.findByDriverId(driverId.toString(), pageable);
        return ResponseEntity.ok(apiMapper.toAssignmentPageApi(result));
    }

    @Override
    public ResponseEntity<VehicleAssignmentPageDTO> getAssignmentsByVehicle(UUID vehicleId, Integer page, Integer size) {
        log.info("Fetching assignments by vehicle: {}", vehicleId);
        var pageable = PageRequest.of(page, size);
        var result = vehicleAssignmentService.findByVehicleId(vehicleId.toString(), pageable);
        return ResponseEntity.ok(apiMapper.toAssignmentPageApi(result));
    }

    @Override
    public ResponseEntity<VehicleAssignmentDTO> updateAssignment(UUID id, VehicleAssignmentRequestDTO vehicleAssignmentRequestDTO) {
        log.info("Updating assignment: {}", id);
        var internalRequest = apiMapper.toAssignmentRequestInternal(vehicleAssignmentRequestDTO);
        var result = vehicleAssignmentService.update(id.toString(), internalRequest);
        return ResponseEntity.ok(apiMapper.toAssignmentApi(result));
    }
}

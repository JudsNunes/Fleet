package com.evolutech.core.fleet.API;

import com.evolutech.core.fleet.mapper.ApiMapper;
import com.evolutech.core.fleet.model.utils.enums.FineStatus;
import com.evolutech.core.fleet.service.FineService;
import com.evolutech.fleet.api.FinesApi;
import com.evolutech.fleet.api.model.FineDTO;
import com.evolutech.fleet.api.model.FinePageDTO;
import com.evolutech.fleet.api.model.FineRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FineController implements FinesApi {

    private final FineService fineService;
    private final ApiMapper apiMapper;

    @Override
    public ResponseEntity<FineDTO> createFine(FineRequestDTO fineRequestDTO) {
        log.info("Creating fine for vehicle: {}", fineRequestDTO.getVehicleId());
        var internalRequest = apiMapper.toFineRequest(fineRequestDTO);
        var result = fineService.save(internalRequest);
        return ResponseEntity.status(201).body(apiMapper.toFineApi(result));
    }

    @Override
    public ResponseEntity<Void> deleteFine(UUID id) {
        log.info("Deleting fine: {}", id);
        fineService.delete(id.toString());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<FinePageDTO> getAllFines(Integer page, Integer size) {
        log.info("Fetching all fines");
        var pageable = PageRequest.of(page, size);
        var result = fineService.findAllPaged(pageable);
        return ResponseEntity.ok(apiMapper.toFinePageApi(result));
    }

    @Override
    public ResponseEntity<FineDTO> getFineById(UUID id) {
        log.info("Fetching fine: {}", id);
        return fineService.findById(id.toString())
                .map(f -> ResponseEntity.ok(apiMapper.toFineApi(f)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<FinePageDTO> getFinesByDriverCpf(String driverCpf, Integer page, Integer size) {
        log.info("Fetching fines by driver CPF: {}", driverCpf);
        var pageable = PageRequest.of(page, size);
        var result = fineService.findByDriverCpf(driverCpf, pageable);
        return ResponseEntity.ok(apiMapper.toFinePageApi(result));
    }

    @Override
    public ResponseEntity<FinePageDTO> getFinesByStatus(String status, Integer page, Integer size) {
        log.info("Fetching fines by status: {}", status);
        var pageable = PageRequest.of(page, size);
        var result = fineService.findByStatus(FineStatus.valueOf(status), pageable);
        return ResponseEntity.ok(apiMapper.toFinePageApi(result));
    }

    @Override
    public ResponseEntity<FinePageDTO> getFinesByVehicle(UUID vehicleId, Integer page, Integer size) {
        log.info("Fetching fines by vehicle: {}", vehicleId);
        var pageable = PageRequest.of(page, size);
        var result = fineService.findByVehicleId(vehicleId.toString(), pageable);
        return ResponseEntity.ok(apiMapper.toFinePageApi(result));
    }

    @Override
    public ResponseEntity<Void> updateFine(UUID id, FineRequestDTO fineRequestDTO) {
        log.info("Updating fine: {}", id);
        var internalRequest = apiMapper.toFineRequest(fineRequestDTO);
        fineService.update(id.toString(), internalRequest);
        return ResponseEntity.ok().build();
    }
}

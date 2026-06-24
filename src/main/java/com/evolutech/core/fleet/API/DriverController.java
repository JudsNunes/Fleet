package com.evolutech.core.fleet.API;

import com.evolutech.core.fleet.mapper.ApiMapper;
import com.evolutech.core.fleet.service.DriverService;
import com.evolutech.fleet.api.DriversApi;
import com.evolutech.fleet.api.model.DriverDTO;
import com.evolutech.fleet.api.model.DriverPageDTO;
import com.evolutech.fleet.api.model.DriverRequestDTO;
import com.evolutech.fleet.api.model.UpdateDriverStatusRequestDTO;
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
public class DriverController implements DriversApi {

    private final DriverService driverService;
    private final ApiMapper apiMapper;

    @Override
    public ResponseEntity<DriverDTO> createDriver(DriverRequestDTO driverRequestDTO) {
        log.info("Creating driver with CPF: {}", driverRequestDTO.getCpf());
        var internalRequest = apiMapper.toDriverRequestInternal(driverRequestDTO);
        var result = driverService.save(internalRequest);
        return ResponseEntity.status(201).body(apiMapper.toDriverApi(result));
    }

    @Override
    public ResponseEntity<Void> deleteDriver(UUID id) {
        log.info("Deleting driver: {}", id);
        driverService.delete(id.toString());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<DriverPageDTO> getAllDrivers(String name, String cpf, String status, String cnhStatus, Integer page, Integer size) {
        log.info("Fetching drivers - page: {}, size: {}", page, size);
        var pageable = PageRequest.of(page, size);
        var drivers = driverService.findByFilters(name, cpf,
                status != null ? com.evolutech.core.fleet.model.utils.enums.DriverStatus.valueOf(status) : null,
                cnhStatus != null ? com.evolutech.core.fleet.model.utils.enums.DriverLicenseStatus.valueOf(cnhStatus) : null,
                pageable);
        return ResponseEntity.ok(apiMapper.toDriverPageApi(drivers));
    }

    @Override
    public ResponseEntity<DriverDTO> getDriverById(UUID id) {
        log.info("Fetching driver: {}", id);
        return driverService.findById(id.toString())
                .map(d -> ResponseEntity.ok(apiMapper.toDriverApi(d)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<DriverDTO> getDriverByCpf(String cpf) {
        log.info("Fetching driver by CPF: {}", cpf);
        return driverService.findByCpf(cpf)
                .map(d -> ResponseEntity.ok(apiMapper.toDriverApi(d)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<DriverDTO>> getExpiringCnhs(Integer days) {
        log.info("Fetching drivers with CNH expiring in {} days", days);
        var result = driverService.findExpiringCnhs(days != null ? days : 30);
        return ResponseEntity.ok(result.stream().map(apiMapper::toDriverApi).collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<Void> updateDriver(UUID id, DriverRequestDTO driverRequestDTO) {
        log.info("Updating driver: {}", id);
        var internalRequest = apiMapper.toDriverRequestInternal(driverRequestDTO);
        driverService.update(id.toString(), internalRequest);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> updateDriverStatus(UUID id, UpdateDriverStatusRequestDTO updateDriverStatusRequestDTO) {
        log.info("Updating status for driver: {}", id);
        var status = com.evolutech.core.fleet.model.utils.enums.DriverStatus.valueOf(updateDriverStatusRequestDTO.getStatus().getValue());
        driverService.updateStatus(id.toString(), status);
        return ResponseEntity.ok().build();
    }
}

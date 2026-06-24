package com.evolutech.core.fleet.API;

import com.evolutech.core.fleet.model.dto.request.DriverRequestDTO;
import com.evolutech.core.fleet.model.dto.response.DriverResponseDTO;
import com.evolutech.core.fleet.model.utils.enums.DriverLicenseStatus;
import com.evolutech.core.fleet.model.utils.enums.DriverStatus;
import com.evolutech.core.fleet.service.DriverService;
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
import java.util.Map;

@RestController
@RequestMapping("/drivers")
@RequiredArgsConstructor
@Slf4j
public class DriverController {

    private final DriverService driverService;

    @GetMapping("/{id}")
    public ResponseEntity<DriverResponseDTO> getDriverById(@PathVariable String id) {
        log.info("Fetching driver: {}", id);
        return driverService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DriverResponseDTO> createDriver(@Valid @RequestBody DriverRequestDTO body) {
        log.info("Creating driver with CPF: {}", body.getCpf());
        var result = driverService.save(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DriverResponseDTO> updateDriver(@PathVariable String id, @Valid @RequestBody DriverRequestDTO body) {
        log.info("Updating driver: {}", id);
        var result = driverService.update(id, body);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<DriverResponseDTO> updateDriverStatus(@PathVariable String id, @RequestBody Map<String, DriverStatus> statusRequest) {
        log.info("Updating status for driver: {}", id);
        var result = driverService.updateStatus(id, statusRequest.get("status"));
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable String id) {
        log.info("Deleting driver: {}", id);
        driverService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<DriverResponseDTO>> getAllDrivers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String cpf,
            @RequestParam(required = false) DriverStatus status,
            @RequestParam(required = false) DriverLicenseStatus cnhStatus,
            @PageableDefault(size = 20, page = 0) Pageable pageable) {
        log.info("Fetching drivers - name: {}, cpf: {}, status: {}, cnhStatus: {}", name, cpf, status, cnhStatus);
        if (name != null || cpf != null || status != null || cnhStatus != null) {
            return ResponseEntity.ok(driverService.findByFilters(name, cpf, status, cnhStatus, pageable));
        }
        return ResponseEntity.ok(driverService.findAllPaged(pageable));
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<DriverResponseDTO> getDriverByCpf(@PathVariable String cpf) {
        log.info("Fetching driver by CPF: {}", cpf);
        return driverService.findByCpf(cpf)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/expiring-cnhs")
    public ResponseEntity<List<DriverResponseDTO>> getExpiringCnhs(
            @RequestParam(defaultValue = "30") int days) {
        log.info("Fetching drivers with CNH expiring in {} days", days);
        return ResponseEntity.ok(driverService.findExpiringCnhs(days));
    }
}

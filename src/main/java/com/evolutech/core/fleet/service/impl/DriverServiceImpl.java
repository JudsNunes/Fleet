package com.evolutech.core.fleet.service.impl;

import com.evolutech.core.fleet.exception.BusinessException;
import com.evolutech.core.fleet.exception.ConflictException;
import com.evolutech.core.fleet.exception.NotFoundException;
import com.evolutech.core.fleet.mapper.DriverMapper;
import com.evolutech.core.fleet.model.dto.request.DriverRequestDTO;
import com.evolutech.core.fleet.model.dto.response.DriverResponseDTO;
import com.evolutech.core.fleet.model.entity.DriverEntity;
import com.evolutech.core.fleet.model.utils.enums.DriverLicenseCategory;
import com.evolutech.core.fleet.model.utils.enums.DriverLicenseStatus;
import com.evolutech.core.fleet.model.utils.enums.DriverStatus;
import com.evolutech.core.fleet.repository.DriverRepository;
import com.evolutech.core.fleet.service.DriverService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;

    @Override
    @Transactional
    public Optional<DriverResponseDTO> findById(String id) {
        log.info("Finding driver by id: {}", id);
        return driverRepository.findById(id).map(driverMapper::toResponseDTO);
    }

    @Override
    @Transactional
    public DriverResponseDTO save(DriverRequestDTO body) {
        log.info("Saving driver with CPF: {}", body.getCpf());

        if (body == null) {
            throw new BusinessException("Body cannot be null");
        }

        driverRepository.findByCpfAndNotDeleted(body.getCpf())
                .ifPresent(existing -> {
                    throw new ConflictException("Driver with CPF " + body.getCpf() + " already exists");
                });

        driverRepository.findByCnhNumberAndNotDeleted(body.getCnhNumber())
                .ifPresent(existing -> {
                    throw new ConflictException("Driver with CNH " + body.getCnhNumber() + " already exists");
                });

        var driverEntity = driverMapper.toEntity(body);
        driverEntity.setStatus(DriverStatus.ACTIVE);

        if (driverEntity.getCnhExpiryDate().isBefore(LocalDate.now())) {
            driverEntity.setCnhStatus(DriverLicenseStatus.EXPIRED);
        } else {
            driverEntity.setCnhStatus(DriverLicenseStatus.ACTIVE);
        }

        var savedDriver = driverRepository.save(driverEntity);
        return driverMapper.toResponseDTO(savedDriver);
    }

    @Override
    @Transactional
    public DriverResponseDTO update(String id, DriverRequestDTO body) {
        log.info("Updating driver: {}", id);

        if (body == null) {
            throw new BusinessException("Body cannot be null");
        }

        var existingDriver = driverRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Driver not found with id: " + id));

        if (!existingDriver.getCpf().equals(body.getCpf())) {
            driverRepository.findByCpfAndNotDeleted(body.getCpf())
                    .ifPresent(d -> {
                        throw new ConflictException("Driver with CPF " + body.getCpf() + " already exists");
                    });
        }

        if (!existingDriver.getCnhNumber().equals(body.getCnhNumber())) {
            driverRepository.findByCnhNumberAndNotDeleted(body.getCnhNumber())
                    .ifPresent(d -> {
                        throw new ConflictException("Driver with CNH " + body.getCnhNumber() + " already exists");
                    });
        }

        existingDriver.setName(body.getName());
        existingDriver.setCpf(body.getCpf());
        existingDriver.setCnhNumber(body.getCnhNumber());
        existingDriver.setCnhCategory(DriverLicenseCategory.valueOf(body.getCnhCategory()));
        existingDriver.setCnhExpiryDate(body.getCnhExpiryDate());
        existingDriver.setPhone(body.getPhone());
        existingDriver.setEmail(body.getEmail());
        existingDriver.setBirthDate(body.getBirthDate());
        existingDriver.setAddress(body.getAddress());

        if (body.getCnhExpiryDate().isBefore(LocalDate.now())) {
            existingDriver.setCnhStatus(DriverLicenseStatus.EXPIRED);
        }

        var updatedDriver = driverRepository.save(existingDriver);
        return driverMapper.toResponseDTO(updatedDriver);
    }

    @Override
    @Transactional
    public DriverResponseDTO updateStatus(String id, DriverStatus status) {
        log.info("Updating status for driver: {} to {}", id, status);

        var existingDriver = driverRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Driver not found with id: " + id));

        existingDriver.setStatus(status);
        var updatedDriver = driverRepository.save(existingDriver);
        return driverMapper.toResponseDTO(updatedDriver);
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.info("Soft-deleting driver: {}", id);

        var driver = driverRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Driver not found with id: " + id));

        driver.setDeletedAt(LocalDateTime.now());
        driverRepository.save(driver);
    }

    @Override
    @Transactional
    public Page<DriverResponseDTO> findAllPaged(Pageable pageable) {
        log.info("Finding all drivers paged: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        return driverRepository.findAllActive(pageable).map(driverMapper::toResponseDTO);
    }

    @Override
    @Transactional
    public Page<DriverResponseDTO> findByFilters(String name, String cpf, DriverStatus status, DriverLicenseStatus cnhStatus, Pageable pageable) {
        log.info("Finding drivers with filters - name: {}, cpf: {}, status: {}, cnhStatus: {}", name, cpf, status, cnhStatus);
        return driverRepository.findByFilters(name, cpf, status, cnhStatus, pageable).map(driverMapper::toResponseDTO);
    }

    @Override
    @Transactional
    public Optional<DriverResponseDTO> findByCpf(String cpf) {
        log.info("Finding driver by CPF: {}", cpf);
        return driverRepository.findByCpfAndNotDeleted(cpf).map(driverMapper::toResponseDTO);
    }

    @Override
    @Transactional
    public List<DriverResponseDTO> findExpiringCnhs(int daysAhead) {
        log.info("Finding drivers with CNH expiring in {} days", daysAhead);
        LocalDate expiryDate = LocalDate.now().plusDays(daysAhead);
        return driverMapper.toResponseDTOList(driverRepository.findExpiringCnhs(expiryDate));
    }
}

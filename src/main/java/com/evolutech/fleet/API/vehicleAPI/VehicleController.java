package com.evolutech.fleet.API.vehicleAPI;


import com.evolutech.fleet.api.VehiclesApi;
import com.evolutech.fleet.service.VehicleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class VehicleController implements VehiclesApi {

    private final VehicleService vehicleService;


}

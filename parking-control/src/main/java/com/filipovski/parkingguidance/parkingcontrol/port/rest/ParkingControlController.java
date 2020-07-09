package com.filipovski.parkingguidance.parkingcontrol.port.rest;

import com.filipovski.parkingguidance.parkingcontrol.application.RampManager;
import com.filipovski.parkingguidance.parkingcontrol.application.dto.RampDto;
import com.filipovski.parkingguidance.parkingcontrol.domain.model.Ramp;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/ramps")
public class ParkingControlController {
    private final RampManager rampManager;

    public ParkingControlController(RampManager rampManager) {
        this.rampManager = rampManager;
    }

    @GetMapping
    public ResponseEntity<List<RampDto>> getRamps() {
        return ResponseEntity.ok(rampManager.findAll().stream()
                .map(RampDto::of)
                .collect(Collectors.toList()));
    }
}

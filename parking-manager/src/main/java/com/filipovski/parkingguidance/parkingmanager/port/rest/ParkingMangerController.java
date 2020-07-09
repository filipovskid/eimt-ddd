package com.filipovski.parkingguidance.parkingmanager.port.rest;

import com.filipovski.parkingguidance.parkingmanager.application.ParkingSlotManager;
import com.filipovski.parkingguidance.parkingmanager.application.dto.ParkingSlotDto;
import com.filipovski.parkingguidance.parkingmanager.application.dto.ParkingSlotReservationDto;
import com.filipovski.parkingguidance.parkingmanager.domain.model.ParkingSlip;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/slots")
public class ParkingMangerController {
    private final ParkingSlotManager parkingSlotManager;

    public ParkingMangerController(ParkingSlotManager parkingSlotManager) {
        this.parkingSlotManager = parkingSlotManager;
    }

    @GetMapping
    public ResponseEntity<List<ParkingSlotDto>> getParkingSlots() {
        List<ParkingSlotDto> parkingSlots = parkingSlotManager.findAll()
                .stream()
                .map(ParkingSlotDto::of)
                .collect(Collectors.toList());

        return ResponseEntity.ok(parkingSlots);
    }

    @PostMapping("/reserve")
    public ResponseEntity reserveParkingSlot(@RequestBody ParkingSlotReservationDto parkingSlotReservation) {
        ParkingSlip parkingSlip = parkingSlotManager.reserveParkingSlot(parkingSlotReservation);

        return ResponseEntity.ok().build();
    }
}

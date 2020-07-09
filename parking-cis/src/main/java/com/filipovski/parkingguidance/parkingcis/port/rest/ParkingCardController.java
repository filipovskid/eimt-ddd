package com.filipovski.parkingguidance.parkingcis.port.rest;

import com.filipovski.parkingguidance.parkingcis.application.ParkingCardService;
import com.filipovski.parkingguidance.parkingcis.application.dto.ParkingCardDto;
import com.filipovski.parkingguidance.parkingcis.domain.model.ParkingCard;
import com.filipovski.parkingguidance.parkingcis.domain.model.ParkingCardId;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/cards")
public class ParkingCardController {
    private final ParkingCardService parkingCardService;

    public ParkingCardController(ParkingCardService parkingCardService) {
        this.parkingCardService = parkingCardService;
    }

    @GetMapping
    public ResponseEntity<List<ParkingCardDto>> getParkingSlots() {
        List<ParkingCardDto> parkingSlots = parkingCardService.findAll()
                .stream()
                .map(ParkingCardDto::of)
                .collect(Collectors.toList());

        return ResponseEntity.ok(parkingSlots);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingCardDto> getParkingCard(@PathVariable("id") ParkingCardId parkingCardId) {
        return parkingCardService.findById(parkingCardId)
                .map(ParkingCardDto::of)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

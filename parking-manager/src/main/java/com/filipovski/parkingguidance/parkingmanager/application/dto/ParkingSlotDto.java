package com.filipovski.parkingguidance.parkingmanager.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.filipovski.parkingguidance.parkingmanager.domain.model.ParkingSlot;
import lombok.NonNull;

public class ParkingSlotDto {
    @JsonProperty("id")
    private String parkingSlotId;

    @JsonProperty("slot_number")
    private String parkingSlotIdentifier;

    @JsonProperty("slot_occupied")
    private boolean isOccupied;

    private ParkingSlotDto(@NonNull String id, String parkingSlotIdentifier, @NonNull Boolean isOccupied) {
        this.parkingSlotId = id;
        this.parkingSlotIdentifier = parkingSlotIdentifier;
        this.isOccupied = isOccupied;
    }

    public static ParkingSlotDto of(@NonNull ParkingSlot parkingSlot) {
        return new ParkingSlotDto(parkingSlot.id().getId(),
                parkingSlot.getIdentifier().identifier(),
                parkingSlot.isSlotOccupied());
    }
}

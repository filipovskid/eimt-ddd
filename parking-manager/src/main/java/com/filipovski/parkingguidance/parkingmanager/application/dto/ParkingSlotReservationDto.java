package com.filipovski.parkingguidance.parkingmanager.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.filipovski.parkingguidance.parkingmanager.domain.model.ParkingCardId;
import com.filipovski.parkingguidance.parkingmanager.domain.model.ParkingSlotId;
import com.sun.istack.NotNull;

import java.io.Serializable;

public class ParkingSlotReservationDto implements Serializable {
    @JsonProperty("parking_slot_id")
    @NotNull
    private ParkingSlotId parkingSlotId;

    @JsonProperty("parking_card_id")
    @NotNull
    private ParkingCardId parkingCardId;

    public ParkingSlotId getParkingSlotId() {
        return parkingSlotId;
    }

    public ParkingCardId getParkingCardId() {
        return parkingCardId;
    }
}

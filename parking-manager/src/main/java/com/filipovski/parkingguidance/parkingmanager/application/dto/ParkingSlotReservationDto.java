package com.filipovski.parkingguidance.parkingmanager.application.dto;

import com.filipovski.parkingguidance.parkingmanager.domain.model.ParkingCardId;
import com.filipovski.parkingguidance.parkingmanager.domain.model.ParkingSlotId;
import com.sun.istack.NotNull;

import java.io.Serializable;

public class ParkingSlotReservationDto implements Serializable {
    @NotNull
    private ParkingSlotId parkingSlotId;

    @NotNull
    private ParkingCardId parkingCardId;

    public ParkingSlotId getParkingSlotId() {
        return parkingSlotId;
    }

    public ParkingCardId getParkingCardId() {
        return parkingCardId;
    }
}

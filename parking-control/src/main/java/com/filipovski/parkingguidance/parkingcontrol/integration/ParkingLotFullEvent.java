package com.filipovski.parkingguidance.parkingcontrol.integration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.filipovski.parkingguidance.parkingcontrol.domain.model.ParkingLotId;
import com.filipovski.parkingguidance.sharedkernel.domain.base.DomainEvent;
import lombok.NonNull;

import java.time.Instant;

public class ParkingLotFullEvent implements DomainEvent {
    @JsonProperty("parkingLotId")
    private ParkingLotId parkingLotId;

    @JsonProperty("occuredOn")
    private final Instant occuredOn;

    public ParkingLotFullEvent(@JsonProperty("parkingLotId") @NonNull ParkingLotId parkingLotId,
                               @JsonProperty("occuredOn") @NonNull Instant occuredOn) {
        this.parkingLotId = parkingLotId;
        this.occuredOn = occuredOn;
    }

    @NonNull
    public ParkingLotId parkingLotId() {
        return parkingLotId;
    }

    @NonNull
    @Override
    public Instant occuredOn() {
        return occuredOn;
    }
}

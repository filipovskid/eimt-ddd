package com.filipovski.parkingguidance.parkingmanager.domain.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.filipovski.parkingguidance.parkingmanager.domain.model.ParkingSlipId;
import com.filipovski.parkingguidance.sharedkernel.domain.base.DomainEvent;
import lombok.NonNull;

import java.time.Instant;

public class ParkingSlipCreated implements DomainEvent {
    @JsonProperty("parkingSlipId")
    private final ParkingSlipId parkingSlipId;

    @JsonProperty("occuredOn")
    private final Instant occuredOn;

    public ParkingSlipCreated(@JsonProperty("parkingSlipId") @NonNull ParkingSlipId parkingSlipId,
                              @JsonProperty("occuredOn") @NonNull Instant occuredOn) {
        this.parkingSlipId = parkingSlipId;
        this.occuredOn = occuredOn;
    }

    @NonNull
    public ParkingSlipId parkingSlipId() {
        return parkingSlipId;
    }

    @Override
    @NonNull
    public Instant occuredOn() {
        return null;
    }
}

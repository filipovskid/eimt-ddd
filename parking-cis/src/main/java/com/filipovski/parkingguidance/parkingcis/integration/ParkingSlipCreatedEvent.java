package com.filipovski.parkingguidance.parkingcis.integration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.filipovski.parkingguidance.parkingcis.domain.model.ParkingCardId;
import com.filipovski.parkingguidance.parkingcis.domain.model.ParkingLotId;
import com.filipovski.parkingguidance.parkingcis.domain.model.ParkingSlipId;
import com.filipovski.parkingguidance.sharedkernel.domain.base.DomainEvent;
import lombok.NonNull;

import java.time.Instant;

public class ParkingSlipCreatedEvent implements DomainEvent {
    @JsonProperty("parkingSlipId")
    private final ParkingSlipId parkingSlipId;

    @JsonProperty("parkingCardId")
    private ParkingCardId parkingCardId;

    @JsonProperty("parkingLotId")
    private ParkingLotId parkingLotId;

    @JsonProperty("occuredOn")
    private final Instant occuredOn;

    public ParkingSlipCreatedEvent(@JsonProperty("parkingSlipId") @NonNull ParkingSlipId parkingSlipId,
                              @JsonProperty("parkingCardId") @NonNull ParkingCardId parkingCardId,
                              @JsonProperty("parkingLotId") @NonNull ParkingLotId parkingLotId,
                              @JsonProperty("occuredOn") @NonNull Instant occuredOn) {
        this.parkingSlipId = parkingSlipId;
        this.parkingCardId = parkingCardId;
        this.parkingLotId = parkingLotId;
        this.occuredOn = occuredOn;
    }

    @NonNull
    public ParkingSlipId parkingSlipId() {
        return parkingSlipId;
    }

    @NonNull
    public ParkingCardId parkingCardId() {
        return parkingCardId;
    }

    @NonNull
    public ParkingLotId parkingLotId() {
        return parkingLotId;
    }

    @Override
    @NonNull
    public Instant occuredOn() {
        return occuredOn;
    }
}
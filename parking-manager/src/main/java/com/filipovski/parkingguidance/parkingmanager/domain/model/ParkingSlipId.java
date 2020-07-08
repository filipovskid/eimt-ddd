package com.filipovski.parkingguidance.parkingmanager.domain.model;

import com.filipovski.parkingguidance.sharedkernel.domain.base.DomainObjectId;
import lombok.NonNull;

import javax.persistence.Embeddable;

@Embeddable
public class ParkingSlipId extends DomainObjectId {

    public ParkingSlipId() { super(""); }

    public ParkingSlipId(@NonNull String id) {
        super(id);
    }
}

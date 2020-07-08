package com.filipovski.parkingguidance.parkingmanager.domain.model;

import com.filipovski.parkingguidance.sharedkernel.domain.base.DomainObjectId;
import org.springframework.lang.NonNull;

import javax.persistence.Embeddable;

@Embeddable
public class ParkingSlotId extends DomainObjectId {

    public ParkingSlotId() {
        super("");
    }

    public ParkingSlotId(@NonNull String id) {
        super(id);
    }
}

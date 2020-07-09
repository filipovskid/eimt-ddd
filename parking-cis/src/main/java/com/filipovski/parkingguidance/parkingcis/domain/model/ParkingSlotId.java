package com.filipovski.parkingguidance.parkingcis.domain.model;

import com.filipovski.parkingguidance.sharedkernel.domain.base.DomainObjectId;
import org.springframework.lang.NonNull;

public class ParkingSlotId extends DomainObjectId {

    public ParkingSlotId() {
        super("");
    }

    public ParkingSlotId(@NonNull String id) {
        super(id);
    }
}
package com.filipovski.parkingguidance.parkingcis.domain.model;

import com.filipovski.parkingguidance.sharedkernel.domain.base.DomainObjectId;

public class ParkingSlipId extends DomainObjectId {

    public ParkingSlipId() {
        super("");
    }

    public ParkingSlipId(String id) {
        super(id);
    }
}

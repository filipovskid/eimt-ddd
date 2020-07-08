package com.filipovski.parkingguidance.parkingmanager.domain.model;

import com.filipovski.parkingguidance.sharedkernel.domain.base.DomainObjectId;

import javax.persistence.Embeddable;

@Embeddable
public class ParkingCardId extends DomainObjectId {

    public ParkingCardId() {
        super("");
    }

    public ParkingCardId(String id) {
        super(id);
    }
}

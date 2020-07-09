package com.filipovski.parkingguidance.parkingcis.domain.model;

import com.filipovski.parkingguidance.sharedkernel.domain.base.DomainObjectId;
import org.springframework.lang.NonNull;

import javax.persistence.Embeddable;

@Embeddable
public class ParkingLotId extends DomainObjectId {

    public ParkingLotId() {
        super("");
    }

    public ParkingLotId(@NonNull String id) {
        super(id);
    }
}

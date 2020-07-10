package com.filipovski.parkingguidance.parkingcontrol.domain.model;

import com.filipovski.parkingguidance.sharedkernel.domain.base.DomainObjectId;
import org.springframework.lang.NonNull;

import javax.persistence.Embeddable;

@Embeddable
public class RampId extends DomainObjectId {

    public RampId() {
        super("");
    }

    public RampId(@NonNull String id) {
        super(id);
    }
}

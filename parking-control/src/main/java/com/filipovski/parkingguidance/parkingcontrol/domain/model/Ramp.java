package com.filipovski.parkingguidance.parkingcontrol.domain.model;

import com.filipovski.parkingguidance.sharedkernel.domain.base.AbstractEntity;
import com.filipovski.parkingguidance.sharedkernel.domain.base.DomainObjectId;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Version;

public class Ramp extends AbstractEntity<RampId> {
    @Version
    private Long version;

    @Column(name = "ramp_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private RampStatus rampStatus;

    public Ramp() { }

    public Ramp(@NonNull RampStatus rampStatus) {
        super(DomainObjectId.randomId(RampId.class));
        this.rampStatus = rampStatus;
    }

    public void open() {
        this.rampStatus = RampStatus.OPENED;
    }

    public void close() {
        this.rampStatus = RampStatus.CLOSED;
    }
}

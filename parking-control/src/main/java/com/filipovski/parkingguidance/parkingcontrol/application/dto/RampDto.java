package com.filipovski.parkingguidance.parkingcontrol.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.filipovski.parkingguidance.parkingcontrol.domain.model.Ramp;
import com.filipovski.parkingguidance.parkingcontrol.domain.model.RampStatus;
import lombok.NonNull;

public class RampDto {
    @JsonProperty("id")
    private String rampId;

    @JsonProperty("ramp_status")
    private RampStatus rampStatus;

    private RampDto(@NonNull String id, @NonNull RampStatus rampStatus) {
        this.rampId = id;
        this.rampStatus = rampStatus;
    }

    public static RampDto of(@NonNull Ramp ramp) {
        return new RampDto(ramp.id().getId(),
                ramp.getRampStatus());
    }
}

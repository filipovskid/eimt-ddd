package com.filipovski.parkingguidance.parkingmanager.domain.model;

import com.filipovski.parkingguidance.sharedkernel.domain.base.ValueObject;

public class SlotIdentifier implements ValueObject {
    private String identifier;

    private SlotIdentifier() { }

    private SlotIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public static SlotIdentifier of(int floor, int slotNumber) {
        if(floor < 0 || slotNumber < 0) {
            throw new IllegalArgumentException("Floor and/or slot numbers incorrect");
        }

        return new SlotIdentifier(String.format("F%d/%d", floor, slotNumber));
    }


}

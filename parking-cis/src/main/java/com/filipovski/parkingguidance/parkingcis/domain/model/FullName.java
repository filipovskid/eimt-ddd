package com.filipovski.parkingguidance.parkingcis.domain.model;

import com.filipovski.parkingguidance.sharedkernel.domain.base.ValueObject;

import java.util.Objects;

public class FullName implements ValueObject {
    private String firstName;

    private String lastName;

    private FullName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public FullName of(String firstName, String lastName) {
        Objects.requireNonNull(firstName, "First name must not be null");
        Objects.requireNonNull(firstName, "Last name must not be null");

        return new FullName(firstName, lastName);
    }

}

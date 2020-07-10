package com.filipovski.parkingguidance.sharedkernel.domain.person;

import com.filipovski.parkingguidance.sharedkernel.domain.base.ValueObject;
import lombok.Getter;

import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Embeddable
public class FullName implements ValueObject {
    private String firstName;

    private String lastName;

    public FullName() {
        this.firstName = null;
        this.lastName = null;
    }

    private FullName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static FullName of(String firstName, String lastName) {
        Objects.requireNonNull(firstName, "First name must not be null");
        Objects.requireNonNull(firstName, "Last name must not be null");

        return new FullName(firstName, lastName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FullName fullName = (FullName) o;
        return firstName.equals(fullName.firstName) &&
                lastName.equals(fullName.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    @Override
    public String toString() {
        return String.format("%s %s", firstName, lastName);
    }
}

package com.filipovski.parkingguidance.sharedkernel.domain.card;

import com.filipovski.parkingguidance.sharedkernel.domain.base.ValueObject;
import lombok.Getter;
import lombok.NonNull;

import javax.persistence.Embeddable;
import java.util.Objects;

// Could be used in different modules, thus stored in shared kernel

@Getter
@Embeddable
public class CardCredit implements ValueObject {
    private int credit;

    public CardCredit() {
        this.credit = 0;
    }

    private CardCredit(int credit) {
        this.credit = credit;
    }

    public static CardCredit of(int numberOfEnters) {
        return new CardCredit(numberOfEnters);
    }

    public CardCredit addCredit(@NonNull CardCredit cardCredit) {
        return new CardCredit(this.credit + cardCredit.credit);
    }

    public CardCredit removeCredit() {
        return new CardCredit(this.credit - 1); // It is allowed to go in minus
    }

    public boolean hasCredit() {
        return credit > 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CardCredit money = (CardCredit) obj;
        return credit == money.credit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(credit);
    }

    @Override
    public String toString() {
        return String.format("%s", credit);
    }

}

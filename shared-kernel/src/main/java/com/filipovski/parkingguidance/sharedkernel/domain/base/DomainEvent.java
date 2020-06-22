package com.filipovski.parkingguidance.sharedkernel.domain.base;

import java.time.Instant;

public interface DomainEvent extends DomainObject {
    Instant occuredOn();
}
